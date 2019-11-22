package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.FieldParser;
import at.technikum.wien.mse.swe.dslconnector.parser.ParserFactory;
import at.technikum.wien.mse.swe.model.RiskCategory;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static at.technikum.wien.mse.swe.dslconnector.ReflectionHelper.createByEmptyConstructor;
import static at.technikum.wien.mse.swe.dslconnector.ReflectionHelper.setFieldValue;
import static java.util.stream.Collectors.toList;

public class Parser {
    private static final Logger LOG = LogManager.getLogger(Parser.class);

    private List<String> supportedAnnotations;

    private void init() {
        supportedAnnotations = new ArrayList<>();
        supportedAnnotations.add(SimpleElement.class.getSimpleName());
        supportedAnnotations.add(ComplexElement.class.getSimpleName());
    }

    public Parser() {
        init();
    }

    public <T> T parse(final String source, final Class<T> c) throws FieldParserException {
        final List<Field> annotatedFields = getAnnotatedFields(c);
        // annotatedFields.forEach(af -> System.out.println("\t\t   field " + af));
        final Map<Field, FieldParser> fieldParsers = getFieldParsersByAnnotation(annotatedFields);

        return parseObject(source, c, annotatedFields, fieldParsers);
    }

    public <T> T parseComplexTypeObject(final String source, final Class<T> c, final List<Field> fields, final Map<Field, FieldParser> fieldParsers) throws FieldParserException {
        // fields.forEach(af -> System.out.println("\t\t   field " + af));
        return parseObject(source, c, fields, fieldParsers);
    }

    private <T> T parseObject(final String source, final Class<T> c, final List<Field> fields, final Map<Field, FieldParser> fieldParsers) throws FieldParserException {
        /// fields.forEach(af -> System.out.println("\t\t   field " + af.getName() + " type: " + af.getType()));

        final T obj;
        if (c.isEnum()) {
            obj = getEnum(source, c, fieldParsers);
        } else {
            if (hasConstructorWithFields(c, fields)) {
                obj = createAndPopulateWithConstructor(c, source, fieldParsers);
            } else {
                obj = createByEmptyConstructor(c);
                populateObject(source, fieldParsers, obj);
            }
        }
        return obj;
    }

    private <T> T getEnum(final String source, final Class<T> c, final Map<Field, FieldParser> fieldParsers) throws FieldParserException {
        LOG.trace("got an enum - this so sad");

        if (fieldParsers.keySet().size() != 1) {
            throw new FieldParserException("error creating obj of type " + c.getSimpleName() + " cant create an enum with more than 1 or less than field. ");
        }

        final Optional<FieldParser> fp = fieldParsers.values().stream().findFirst();
        final String enumValue = fp.get().parseValue(source);

        if (StringUtils.isEmpty(enumValue)) {
            return null;
        }
        return (T) RiskCategory.fromCode(enumValue).orElseThrow(() -> new FieldParserException("can't read enum at field with position "));
    }

    private <T> boolean hasConstructorWithFields(final Class<T> c, final List<Field> fields) {
        final List<Class> constructorArgTypes = fields.stream()
                .map(Field::getType)
                .collect(toList());

        // constructorArgTypes.forEach(carg -> System.out.println("\t\t   construcotr args   " + carg.getSimpleName()));

        // \_(ツ)_/¯
        Class[] cl = constructorArgTypes.toArray(new Class[0]);
        Constructor<?> cons = null;

        try {
            cons = c.getConstructor(cl);
        } catch (Exception e) {
            LOG.info("hasConstructorWithFields   no constructor with args " + constructorArgTypes);
            return false;
        }
        return true;
    }

    private <T> T createAndPopulateWithConstructor(final Class<T> c, final String source, final Map<Field, FieldParser> fieldParsers) throws FieldParserException {
        // create an array with the types the constructor must provide
        final List<Class> constructorArgTypes = fieldParsers.keySet().stream()
                .map(Field::getType)
                .collect(toList());

        /// constructorArgTypes.forEach(carg -> System.out.println("\t\t   construcotr args   " + carg.getSimpleName()));


        // \_(ツ)_/¯
        Class[] cl = constructorArgTypes.toArray(new Class[0]);

        Constructor<?> cons = null;
        try {
            cons = c.getConstructor(cl);
        } catch (Exception e) {
            throw new FieldParserException("no constructor found for argument types " + constructorArgTypes);
        }

        final Object[] args = fieldParsers.values().stream()
                .map(parser -> {
                    try {
                        return parser.parseValue(source);
                    } catch (FieldParserException e) {
                        LOG.error("exception in parseValue: " + e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toArray();

        final T obj;
        try {
            obj = (T) cons.newInstance(args);
        } catch (Exception e) {
            throw new FieldParserException("error creating obj of type " + c.getSimpleName() + " using constructor types: " + constructorArgTypes + " and param values " + args);
        }

        return obj;
    }

    private <T> void populateObject(final String source, final Map<Field, FieldParser> fieldParsers, final T obj) {
        fieldParsers.forEach((field, parser) -> {
            try {
                setFieldValue(obj, field, parser.parseValue(source));
            } catch (FieldParserException e) {
                LOG.error("error populateObject  call to 'setFieldValue'      " + e.getMessage());
            }
        });
    }

    private Map<Field, FieldParser> getFieldParsersByAnnotation(final List<Field> fields) {
        final Map<Field, FieldParser> fieldParsers = new LinkedHashMap<>();
        fields.forEach(f -> {
            try {
                fieldParsers.put(f, getParserByAnnotation(f));
            } catch (FieldParserException e) {
                LOG.error("error getFieldParsers call to 'getParser': " + e.getMessage());
            }
        });
        return fieldParsers;
    }

    private FieldParser getParserByAnnotation(final Field f) throws FieldParserException {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        if (annotations.size() != 1) {
            throw new FieldParserException("found " + annotations.size() + " annotations, but can only handle 1 for each field");
        }

        final FieldParser parser = annotations.stream()
                .map(a -> ParserFactory.getParserByAnnotation(f.getType(), a))
                .findFirst()
                .orElseThrow(() -> new FieldParserException("No Parser found for field '" + f.getName() + " annotation: " + annotations.get(0).annotationType().getSimpleName()));
        return parser;
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
}
