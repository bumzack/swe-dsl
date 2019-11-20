package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import org.apache.commons.lang.StringUtils;

public class NumLongParser extends AbstractFieldParser<Long> {

    public NumLongParser(int pos, int len, AlignmentEnum alignment, char padding) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
    }

    public Long parseValue(final String source) throws FieldParserException {
        final String parsedString = parse(source);
        if (StringUtils.isEmpty(parsedString)) {
            return 0L;
        }
        try {
            return Long.valueOf(parsedString);
        } catch (NumberFormatException e) {
            throw new FieldParserException("cant read integer at field with position " + pos + ", len: " + len + " in source string '" + source + "'");
        }
    }
}
