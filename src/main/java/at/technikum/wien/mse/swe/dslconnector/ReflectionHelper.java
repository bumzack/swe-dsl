package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ReflectionHelper {
    private static final Logger LOG = LogManager.getLogger(ReflectionHelper.class);

    static public Optional<Method> getValueOfStringMethod(final Class c) {
        return getMethodsWithStringParameter(c).stream()
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), "valueOf"))
                .findFirst();
    }

    static public Method getSetterMethod(final Class c, final String name) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        final Method method = methods.stream()
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), name))
                .findFirst()
                .orElse(null);
        return method;
    }

    static public <T> T createByEmptyConstructor(Class<T> c) throws FieldMapperException {
        final T obj;
        try {
            obj = c.newInstance();
        } catch (Exception e) {
            LOG.error("error creating new object with empty constructor  of type    " + c.getSimpleName() + " " + e);
            throw new FieldMapperException("createByEmptyConstructor    error creating a new object of type    " + c.getSimpleName() + "      " + e.getMessage());
        }
        return obj;
    }

    static public <T, U> void setFieldValue(final T o, final Field field, final U value) throws FieldMapperException {
        final String setter = StringUtils.join(new String[]{"set", StringUtils.capitalize(field.getName())});

        try {
            Class tmp = o.getClass();

            final Method setterMethod = getSetterMethod(tmp, setter);
            if (setterMethod == null) {
                throw new FieldMapperException(String.format("no setter method found for field " + field.getName()));
            }

            setterMethod.invoke(o, value);

        } catch (Exception ex) {
            LOG.error(String.format("error setting the value of field '%s'.     ex %s", field.getName(), ex.getMessage()));
            throw new FieldMapperException(String.format("error setting the value of field '%s'.  exception: %s", field.getName(), ex.getMessage()));
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

    public static List<Method> getMethodsWithStringParameter(final Class c) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        return methods.stream()
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].equals(String.class))
                .collect(Collectors.toList());
    }

    // no constructor required/allowed with enum
    // just call valueOf or any other method that returns an enum ...
    public static <T> T getEnum(final String source, final Class<T> c) throws FieldMapperException {
        final List<Method> stringMethods = ReflectionHelper.getMethodsWithStringParameter(c);
        for (Method m : stringMethods) {
            try {
                //  \_(ツ)_/¯: passing null for the obj :-(
                final Object obj = m.invoke(null, source);
                // RiskCategory returns an Optional<RiskCategory>
                // unpack the RiskCategory
                if (obj.getClass().equals(Optional.class)) {
                    final Optional<T> a = (Optional<T>) obj;
                    return a.get();
                } else {
                    return (T) obj;
                }

            } catch (Exception e) {
                LOG.info("setter method " + m.getName() + " failed to set value" + e.getMessage());
            }
        }
        return null;
    }

    public static <T, U> T createSimpleObject(final U parsed, final Class<?> type) {
        try {
            final Method valueOf = ReflectionHelper.getValueOfStringMethod(type).get();
            final T obj = (T) valueOf.invoke(null, parsed);
            return obj;
        } catch (Exception e) {
            LOG.error("cant create object of type  " + type.getName() + " or error exceuting invoke():  " + e.getMessage());
        }
        return null;
    }

    public static <T, U> T createBigDecimalObject(final Double d) {
        final List<Method> valueOfs = getValueOfMethods(BigDecimal.class);
        for (Method m : valueOfs) {
            try {
                final T obj = (T) m.invoke(null, d);
                return obj;
            } catch (Exception e) {
                LOG.info("cant create object of type  BigDecimal  or error exceuting invoke():  " + e.getMessage());
            }
        }
        LOG.error("cant create object of type  BigDecimal    or error exceuting invoke():  ");
        return null;
    }

    public static List<Method> getValueOfMethods(final Class c) {
        final List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        return methods.stream()
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> StringUtils.equalsIgnoreCase(m.getName(), "valueOf"))
                .collect(Collectors.toList());
    }

//

//    static private <T> boolean hasConstructorFromString(final Class<T> c, final List<Field> fields) {
//        final List<Class> constructorArgTypes = fields.stream()
//                .map(Field::getType)
//                .collect(toList());
//
//        // \_(ツ)_/¯
//        Class[] cl = constructorArgTypes.toArray(new Class[0]);
//        Constructor<?> cons = null;
//
//        try {
//            cons = c.getConstructor(cl);
//        } catch (Exception e) {
//            LOG.info("hasConstructorWithFields   no constructor with args " + constructorArgTypes);
//            return false;
//        }
//        return true;
//    }

}
