package at.technikum.wien.mse.swe.dslconnector.mapper.impl;

import at.technikum.wien.mse.swe.dslconnector.ReflectionHelper;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.dslconnector.mapper.AbstractFieldMapper;
import at.technikum.wien.mse.swe.dslconnector.mapper.dto.SimpleTypeDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class SimpleTypeMapper extends AbstractFieldMapper {
    private static final Logger LOG = LogManager.getLogger(SimpleTypeMapper.class);

    private Field field;

    public SimpleTypeMapper(final Field f, final SimpleTypeDto simpleTypeDto) {
        this.field = f;
        this.simpleTypeDto = simpleTypeDto;
    }

    public <T> T mapValue(final String source) throws FieldMapperException {
        final String parsed = parseString(source);
        if (field.getType().getTypeName().equals(String.class.getTypeName())) {
            return (T) parsed;
        } else if (field.getType().isEnum()) {
            return (T) ReflectionHelper.getEnum(parsed, field.getType());
        } else {
            return (T) mapType(parsed, field);
        }
    }

    private <T> T mapType(final String parsed, final Field field) throws FieldMapperException {
        if (field.getType().equals(BigDecimal.class)) {
            try {
                final Double d = ReflectionHelper.createSimpleObject(parsed, Double.class);
                final BigDecimal bd = ReflectionHelper.createBigDecimalObject(d);
                return (T) bd;
            } catch (Exception e) {
                LOG.error("cant convert source '" + parsed + "' to BigDecimal");
                throw new FieldMapperException("cant convert source '" + parsed + "' to BigDecimal");
            }
        }

        try {
            return (T) ReflectionHelper.createSimpleObject(parsed, field.getType());
        } catch (Exception e) {
            LOG.error("cant convert source '" + parsed + "' to type " + field.getType().getName());
            throw new FieldMapperException("cant convert source '" + parsed + "' to BigDecimal");
        }
    }
}
