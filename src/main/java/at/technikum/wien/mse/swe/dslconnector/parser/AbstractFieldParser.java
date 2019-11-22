package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractFieldParser implements FieldParser {

    protected int pos;
    protected int len;
    protected AlignmentEnum alignment;
    protected char padding;

    @Override
    public abstract <T> T parseValue(final String source) throws FieldParserException;

    protected String parse(final String source) {
        // System.out.println("parse()   pos = " + pos + " len = " + len + "    s.len = " + source.length() + "       s = '" + source + "'");
        final String paddingString = String.valueOf(padding);
        final String fieldString = source.substring(pos, pos + len);
        // System.out.println("parse()   pos = " + pos + " len = " + len + "         s = '" + fieldString + "'");
        String fieldStringTrimmed = "";

        if (alignment.equals(AlignmentEnum.LEFT)) {
            fieldStringTrimmed = StringUtils.stripEnd(fieldString, paddingString);
        } else {
            fieldStringTrimmed = StringUtils.stripStart(fieldString, paddingString);
        }
        // System.out.println("parse()   pos = " + pos + " len = " + len + "  trimmed s = '" + fieldStringTrimmed + "'");

        return fieldStringTrimmed;
    }


}
