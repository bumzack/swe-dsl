package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.FieldParser;
import at.technikum.wien.mse.swe.dslconnector.parser.ParserFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static at.technikum.wien.mse.swe.dslconnector.ReflectionHelper.*;
import static java.util.stream.Collectors.toList;

public class Parser {

    private List<String> supportedAnnotations;

    public void init() {
        supportedAnnotations = new ArrayList<>();
        supportedAnnotations.add(DepotOwnerField.class.getSimpleName());
        supportedAnnotations.add(RiskCategoryField.class.getSimpleName());
        supportedAnnotations.add(AmountField.class.getSimpleName());
        supportedAnnotations.add(IsinField.class.getSimpleName());
        supportedAnnotations.add(SimpleElement.class.getSimpleName());
    }

    public Parser() {
        init();
    }

    public <T> T parse(final String source, final Class<T> c) throws FieldParserException {

        final List<Field> annotatedFields = getAnnotatedFields(c);

        annotatedFields.forEach(af -> System.out.println("\t\t   field " + af.getName() + " type: " + af.getType()));

        final Map<Field, FieldParser> fieldParsers = getFieldParsers(annotatedFields);

        final T obj;
        if (hasAllSetters(annotatedFields, c)) {
            obj = createByEmptyConstructor(c);
            populateObject(source, fieldParsers, obj);
        } else {
            obj = createAndPopulateWithConstructor(c, source, fieldParsers);
        }
        return obj;
    }

    private <T> T createAndPopulateWithConstructor(Class<T> c, String source, Map<Field, FieldParser> fieldParsers) throws FieldParserException {
        // create an array with the types the constructor must provide
        final List<Class> constructorArgTypes = fieldParsers.keySet().stream()
                .map(Field::getType)
                .collect(toList());

        constructorArgTypes.forEach(carg -> System.out.println("\t\t   construcotr args   " + carg.getSimpleName()));


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
                        System.out.println("exception in parseValue: " + e.getMessage());
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
                System.out.println("error populateObject  call to 'setFieldValue2'\n   " + e.getMessage());
            }
        });
    }

    private Map<Field, FieldParser> getFieldParsers(List<Field> annotatedFields) {
        final Map<Field, FieldParser> fieldParsers = new LinkedHashMap<>();
        annotatedFields.forEach(f -> {
            try {
                fieldParsers.put(f, getParser(f));
            } catch (FieldParserException e) {
                System.out.println("error getFieldParsers call to 'getParser': " + e.getMessage());
            }
        });
        return fieldParsers;
    }

    private <T> List<Field> getAnnotatedFields(Class<T> c) {
        final List<Field> fields = Arrays.asList(c.getDeclaredFields());
        return fields.stream()
                .filter(this::filterAnnotatedFields)
                .collect(toList());
    }

    private FieldParser getParser(final Field f) throws FieldParserException {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        if (annotations.size() != 1) {
            throw new FieldParserException("found " + annotations.size() + " annotations, nut can only handle 1 for each field");
        }

        final FieldParser parser = annotations.stream()
                .map(a -> ParserFactory.getParserForType(f.getType().getTypeName(), a))
                .findFirst()
                .orElseThrow(() -> new FieldParserException("No Parser found for field '" + f.getName()));
        return parser;
    }

    private boolean filterAnnotatedFields(final Field f) {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        final boolean res = annotations.stream()
                .anyMatch(a -> supportedAnnotations.contains(a.annotationType().getSimpleName()));
        return res;
    }
}
