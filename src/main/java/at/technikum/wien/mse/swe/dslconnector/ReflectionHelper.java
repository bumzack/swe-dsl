package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReflectionHelper {
    private static final Logger LOG = LogManager.getLogger(ReflectionHelper.class);

    static public Method getSetterMethod(final Class c, final String name) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        final Method method = methods.stream()
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), name))
                .findFirst()
                .orElse(null);
        return method;
    }

    static public <T> T createByEmptyConstructor(Class<T> c) throws FieldParserException {
        final T obj;
        try {
            obj = c.newInstance();
        } catch (Exception e) {
            LOG.error("error creating new object with empty constructor  of type    " + c.getSimpleName() + " " + e);
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
            LOG.error(String.format("error setting the value of field '%s'.     ex %s", field.getName(), ex.getMessage()));
            throw new FieldParserException(String.format("error setting the value of field '%s'.  exception: %s", field.getName(), ex.getMessage()));
        }
    }

    static public <T> boolean hasConstructorWithFields(final Class<T> c, final List<Field> fields) {
        final List<Class> constructorArgTypes = fields.stream()
                .map(Field::getType)
                .collect(toList());

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
}
