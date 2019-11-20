package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class BigDecimalParser extends AbstractFieldParser<BigDecimal>{
    public BigDecimalParser(int pos, int len, AlignmentEnum alignment, char padding) {
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
            return BigDecimal.valueOf(Double.valueOf(parsedString));
        } catch (NumberFormatException e) {
            throw new FieldParserException("cant read double at field with position " + pos + ", len: " + len + " in source string '" + source + "'");
        }
    }


}
