package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.*;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    private Map<String, Class> supportedAnnotations;

    public void init() {
        supportedAnnotations = new HashMap<>();
        supportedAnnotations.put(StringField.class.getSimpleName(), StringParser.class);
        supportedAnnotations.put(BigDecimalField.class.getSimpleName(), BigDecimalParser.class);
        supportedAnnotations.put(DepotOwnerField.class.getSimpleName(), DeptOwnerParser.class);
        supportedAnnotations.put(NumberInt.class.getSimpleName(), NumLongParser.class);
        supportedAnnotations.put(RiskCategoryField.class.getSimpleName(), RiskCategoryParser.class);
        supportedAnnotations.put(AmountField.class.getSimpleName(), AmountParser.class);
        supportedAnnotations.put(IsinField.class.getSimpleName(), IsinParser.class);
    }

    public Parser() {
        init();
    }

    public <T> T parse(final String source, final Class<T> c) throws FieldParserException {

        final List<Field> annotatedFields = getAnnotatedFields(c);

        final Map<Field, FieldParser> fieldParsers = getFieldParsers(annotatedFields);

        final T obj = createEmptyObject(c);

        populateObject(source, fieldParsers, obj);

        return obj;
    }

    private <T> void populateObject(String source, Map<Field, FieldParser> fieldParsers, T obj) {
        fieldParsers.forEach((field, parser) -> {
            try {
                setFieldValue(obj, field, parser.parseValue(source));
            } catch (FieldParserException e) {
                System.out.println("error parseSecurityAccountOverview call to 'setFieldValue2'\n   " + e.getMessage());
            }
        });
    }

    private Map<Field, FieldParser> getFieldParsers(List<Field> annotatedFields) {
        final Map<Field, FieldParser> fieldParsers = new HashMap<>();
        annotatedFields.forEach(f -> {
            try {
                fieldParsers.put(f, getParser(f));
            } catch (FieldParserException e) {
                System.out.println("error parseSecurityAccountOverview call to 'getParser': " + e.getMessage());
            }
        });
        return fieldParsers;
    }

    private <T> List<Field> getAnnotatedFields(Class<T> c) {
        final List<Field> fields = Arrays.asList(c.getDeclaredFields());
        return fields.stream()
                .filter(this::filterAnnotatedFields)
                .collect(Collectors.toList());
    }

    private <T> T createEmptyObject(Class<T> c) throws FieldParserException {
        final T obj;
        try {
            obj = c.newInstance();
        } catch (Exception e) {
            throw new FieldParserException("error creating a new objeckt of type    " + c.getSimpleName() + "      " + e.getMessage());
        }
        return obj;
    }

    private <T, U> void setFieldValue(final T o, final Field field, final U value) throws FieldParserException {
        final String setter = StringUtils.join(new String[]{"set", StringUtils.capitalize(field.getName())});

        try {
            Class tmp = o.getClass();

            final Method setterMethod = getSetterMethod(tmp, setter);

            // bold move, but setter has always 1 parameter ...
            final String fieldType = setterMethod.getParameterTypes()[0].getSimpleName();

            setterMethod.invoke(o, value);

        } catch (Exception ex) {
            System.out.println(String.format("error setting the value of field '%s'.     ex %s", field.getName(), ex.getMessage()));
            throw new FieldParserException(String.format("error setting the value of field '%s'.  exception: %s", field.getName(), ex.getMessage()));
        }
    }

    private FieldParser getParser(final Field f) throws FieldParserException {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        if (annotations.size() != 1) {
            throw new FieldParserException("found " + annotations.size() + " annotations, nut can only handle 1 for each field");
        }

        final FieldParser parser = annotations.stream()
                .map(a -> {
                    // TODO: try to use the  map here, otherwise the map is useless
                    if (a instanceof StringField) {
                        StringField field = (StringField) a;
                        return new StringParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
                    }

                    if (a instanceof BigDecimalField) {
                        BigDecimalField field = (BigDecimalField) a;
                        return new BigDecimalParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
                    }

                    if (a instanceof RiskCategoryField) {
                        RiskCategoryField field = (RiskCategoryField) a;
                        return new RiskCategoryParser(field.position(), field.length(), field.align(), field.padding(), field.paddingChar());
                    }

                    if (a instanceof DepotOwnerField) {
                        DepotOwnerField field = (DepotOwnerField) a;
                        return new DeptOwnerParser(field.position(), field.lengthFirstName(), field.lengthLastName(), field.align(), field.padding(), field.paddingChar());
                    }

                    if (a instanceof AmountField) {
                        AmountField field = (AmountField) a;
                        return new AmountParser(field.positionBalance(), field.lengthBalance(), field.alignBalance(), field.paddingBalance(), field.paddingCharacterBalance(),
                                field.positionCurrency(), field.lengthCurrency(), field.alignCurrency(), field.paddingCurrency(), field.paddingCharacterCurrency());
                    }

                    if (a instanceof IsinField) {
                        IsinField field = (IsinField) a;
                        return new IsinParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
                    }
                    return null;
                })
                .findFirst()
                .orElseThrow(() -> new FieldParserException("No Parser found for field '" + f.getName()));
        return parser;
    }

    private boolean filterAnnotatedFields(final Field f) {
        final List<Annotation> annotations = Arrays.asList(f.getAnnotations());
        final boolean res = annotations.stream()
                .anyMatch(a -> supportedAnnotations.containsKey(a.annotationType().getSimpleName()));
        return res;
    }

    private Method getSetterMethod(final Class c, final String name) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        final Method method = methods.stream()
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), name))
                .findFirst()
                .orElse(null);
        return method;
    }
}
