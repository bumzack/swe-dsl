package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.dslconnector.mapper.FieldMapper;
import at.technikum.wien.mse.swe.dslconnector.mapper.dto.SimpleTypeDto;
import at.technikum.wien.mse.swe.dslconnector.mapper.impl.ComplexTypeMapper;
import at.technikum.wien.mse.swe.dslconnector.mapper.impl.SimpleTypeMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static at.technikum.wien.mse.swe.dslconnector.ReflectionHelper.*;
import static java.util.stream.Collectors.toList;

public class GenericMapper {
    private static final Logger LOG = LogManager.getLogger(GenericMapper.class);

    private List<String> supportedAnnotations;

    private void init() {
        supportedAnnotations = new ArrayList<>();
        supportedAnnotations.add(SimpleElement.class.getSimpleName());
        supportedAnnotations.add(ComplexElement.class.getSimpleName());
    }

    public GenericMapper() {
        init();
    }

    public <T> T map(final String source, final Class<T> c) throws FieldMapperException {
        final List<Field> annotatedFields = getAnnotatedFields(c);
        final Map<Field, FieldMapper> fieldMapper = getFieldMapperByAnnotation(annotatedFields);

        return mapToObject(source, c, annotatedFields, fieldMapper);
    }

    public <T> T mapComplexTypeToObject(final String source, final Class<T> c, final List<Field> fields, final Map<Field, FieldMapper> fieldMapper) throws FieldMapperException {
        return mapToObject(source, c, fields, fieldMapper);
    }

    private <T> T mapToObject(final String source, final Class<T> c, final List<Field> fields, final Map<Field, FieldMapper> fieldMapper) throws FieldMapperException {

        final T obj;
        if (hasConstructorWithFields(c, fields)) {
            obj = createAndPopulateWithConstructor(c, source, fieldMapper);
        } else {
            obj = createByEmptyConstructor(c);
            populateObject(source, fieldMapper, obj);
        }
        return obj;
    }

    private <T> T getEnum(final String source, final Class<T> c, final Map<Field, FieldMapper> fieldMapper) throws FieldMapperException {
        if (fieldMapper.keySet().size() != 1) {
            LOG.error("error creating obj of type " + c.getSimpleName() + " cant create an enum with more than 1 or less than 1 field. ");
            throw new FieldMapperException("error creating obj of type " + c.getSimpleName() + " cant create an enum with more than 1 or less than field. ");
        }
        return ReflectionHelper.getEnum(source, c);
    }

    private <T> T createAndPopulateWithConstructor(final Class<T> c, final String source, final Map<Field, FieldMapper> fieldMapper) throws FieldMapperException {
        final List<Class> constructorArgTypes = fieldMapper.keySet().stream()
                .map(Field::getType)
                .collect(toList());

        // \_(ツ)_/¯
        Class[] cl = constructorArgTypes.toArray(new Class[0]);

        Constructor<?> cons = null;
        try {
            cons = c.getConstructor(cl);
        } catch (Exception e) {
            throw new FieldMapperException("no constructor found for argument types " + constructorArgTypes);
        }

        final Object[] args = fieldMapper.values().stream()
                .map(mapper -> {
                    try {
                        return mapper.mapValue(source);
                    } catch (FieldMapperException e) {
                        LOG.error("exception in mapValue: " + e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toArray();

        final T obj;
        try {
            obj = (T) cons.newInstance(args);
        } catch (Exception e) {
            throw new FieldMapperException("error creating obj of type " + c.getSimpleName() + " using constructor types: " + constructorArgTypes + " and param values " + args);
        }

        return obj;
    }

    private <T> void populateObject(final String source, final Map<Field, FieldMapper> fieldMapper, final T obj) {
        fieldMapper.forEach((field, mapper) -> {
            try {
                setFieldValue(obj, field, mapper.mapValue(source));
            } catch (FieldMapperException e) {
                LOG.error("error populateObject  call to 'setFieldValue'      " + e.getMessage());
            }
        });
    }

    private Map<Field, FieldMapper> getFieldMapperByAnnotation(final List<Field> fields) {
        final Map<Field, FieldMapper> fieldMapper = new LinkedHashMap<>();
        fields.forEach(f -> {
            try {
                fieldMapper.put(f, getMapperByAnnotation(f));
            } catch (FieldMapperException e) {
                LOG.error("error getFieldMappers call to 'getMapperByAnnotation': " + e.getMessage());
            }
        });
        return fieldMapper;
    }

    private <T> List<Field> getAnnotatedFields(final Class<T> c) {
        final List<Field> fields = Arrays.asList(c.getDeclaredFields());
        return fields.stream()
                .filter(this::filterAnnotatedFields)
                .collect(toList());
    }

    private boolean filterAnnotatedFields(final Field f) {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        final boolean res = annotations.stream()
                .anyMatch(a -> supportedAnnotations.contains(a.annotationType().getSimpleName()));
        return res;
    }

    private FieldMapper getMapperByAnnotation(final Field f) throws FieldMapperException {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        if (annotations.size() != 1) {
            throw new FieldMapperException("found " + annotations.size() + " annotations, but can only handle 1 for each field");
        }

        final FieldMapper mapper = annotations.stream()
                .map(a -> getMapperByAnnotation(f, a))
                .findFirst()
                .orElseThrow(() -> new FieldMapperException("No mapper found for field '" + f.getName() + " annotation: " + annotations.get(0).annotationType().getSimpleName()));
        return mapper;
    }

    static public FieldMapper getMapperByAnnotation(final Field field, final Annotation a) {
        if (a instanceof SimpleElement) {
            final SimpleElement element = (SimpleElement) a;
            final SimpleTypeDto simpleTypeDto = SimpleTypeDto.map(element);
            return new SimpleTypeMapper(field, simpleTypeDto);
        }

        if (a instanceof ComplexElement) {
            final ComplexElement element = (ComplexElement) a;
            return new ComplexTypeMapper(field, element);
        }
        return null;
    }
}
