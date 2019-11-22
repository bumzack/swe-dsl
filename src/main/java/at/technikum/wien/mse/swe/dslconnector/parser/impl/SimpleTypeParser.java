package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.AbstractFieldParser;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.dto.SimpleTypeDto;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class SimpleTypeParser extends AbstractFieldParser {
    private Type t;

    public SimpleTypeParser(final Type type, final SimpleTypeDto simpleTypeDto) {
        this.t = type;
        this.simpleTypeDto = simpleTypeDto;
    }

    public <T> T parseValue(final String source) throws FieldParserException {
        final String parsed = parse(source);
        if (t.getTypeName().equals(String.class.getTypeName())) {
            return (T) StringUtils.trim(parsed);
        } else if (t.getTypeName().equals(BigDecimal.class.getTypeName())) {
            return (T) convertStringToBigDecimal(parsed);
        }
        return null;
    }

    private BigDecimal convertStringToBigDecimal(final String parsed) throws FieldParserException {
        if (StringUtils.isEmpty(parsed)) {
            return BigDecimal.ZERO;
        }

        try {
            Double d = Double.parseDouble(parsed);
            return BigDecimal.valueOf(d);
        } catch (NumberFormatException e) {
            throw new FieldParserException("cant read bigdecimal from parsed string '" + parsed + "'");
        }
    }
}
