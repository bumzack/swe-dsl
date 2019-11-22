package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectionHelper {

    static public <T> boolean hasAllSetters(final List<Field> annotatedFields, final Class<T> c) {
        annotatedFields.forEach(af -> System.out.println("fieldname: " + af.getName()));
        return annotatedFields.stream()
                .allMatch(field -> hasSetterMethod(c, field.getName()));
    }

    static public Method getSetterMethod(final Class c, final String name) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        final Method method = methods.stream()
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), name))
                .findFirst()
                .orElse(null);
        return method;
    }

    static public boolean hasSetterMethod(final Class c, final String name) {
        final String setter = StringUtils.join(new String[]{"set", StringUtils.capitalize(name)});
        System.out.println("setter name: " + setter);
        return getSetterMethod(c, setter) != null;
    }

    static public <T> T createByEmptyConstructor(Class<T> c) throws FieldParserException {
        final T obj;
        try {
            obj = c.newInstance();
        } catch (Exception e) {
            throw new FieldParserException("createByEmptyConstructor    error creating a new object of type    " + c.getSimpleName() + "      " + e.getMessage());
        }
        return obj;
    }

    static public <T, U> void setFieldValue(final T o, final Field field, final U value) throws FieldParserException {
        final String setter = StringUtils.join(new String[]{"set", StringUtils.capitalize(field.getName())});

        try {
            Class tmp = o.getClass();

            final Method setterMethod = getSetterMethod(tmp, setter);
            if (setterMethod == null) {
                throw new FieldParserException(String.format("no setter method found for field " + field.getName()));
            }

            setterMethod.invoke(o, value);

        } catch (Exception ex) {
            System.out.println(String.format("error setting the value of field '%s'.     ex %s", field.getName(), ex.getMessage()));
            throw new FieldParserException(String.format("error setting the value of field '%s'.  exception: %s", field.getName(), ex.getMessage()));
        }
    }
}
