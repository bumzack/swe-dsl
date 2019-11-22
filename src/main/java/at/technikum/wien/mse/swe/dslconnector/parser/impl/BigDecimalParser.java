package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.AbstractFieldParser;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class BigDecimalParser extends AbstractFieldParser {
    public BigDecimalParser(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
    }

    public BigDecimal parseValue(final String source) throws FieldParserException {
        final String parsedString = parse(source);
        if (StringUtils.isEmpty(parsedString)) {
            return BigDecimal.ZERO;
        }

        try {
            Double d = Double.parseDouble(parsedString);
            return BigDecimal.valueOf(d);
        } catch (NumberFormatException e) {
            throw new FieldParserException("cant read bigdecimal at field with position " + pos + ", len: " + len + " in source string '" + source + "'");
        }
    }
}
