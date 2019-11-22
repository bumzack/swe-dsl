package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import org.apache.commons.lang.StringUtils;

public class StringParser implements FieldParser {

    private boolean padding;
    private int pos;
    private int len;
    private AlignmentEnum alignment;
    private char paddingChar;

    public StringParser(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingChar) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = true;
        this.paddingChar = paddingChar;
    }

    @Override
    public String parseValue(final String source) {
        return parse(source);
    }

    // TODO duplicate code - find another abtract class?
    protected String parse(final String source) {
        // System.out.println("parse()   pos = " + pos + " len = " + len + "    s.len = " + source.length() + "       s = '" + source + "'");
        final String paddingString = String.valueOf(paddingChar);
        final String fieldString = source.substring(pos, pos + len);
        // System.out.println("parse()   pos = " + pos + " len = " + len + "         s = '" + fieldString + "'");

        if (padding) {
            String fieldStringTrimmed = "";
            if (alignment.equals(AlignmentEnum.LEFT)) {
                fieldStringTrimmed = StringUtils.stripEnd(fieldString, paddingString);
            } else {
                fieldStringTrimmed = StringUtils.stripStart(fieldString, paddingString);
            }
            // System.out.println("parse()   pos = " + pos + " len = " + len + "  trimmed s = '" + fieldStringTrimmed + "'");
            return fieldStringTrimmed;
        }

        // System.out.println("parse()    no trimming required  pos = " + pos + " len = " + len + "   s = '" + fieldString + "'");

        return fieldString;
    }
}
