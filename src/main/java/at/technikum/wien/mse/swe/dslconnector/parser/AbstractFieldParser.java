package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractFieldParser implements FieldParser {

    protected int pos;
    protected int len;
    protected AlignmentEnum alignment;
    protected boolean padding;
    protected char paddingCharacter;

    @Override
    public abstract <T> T parseValue(final String source) throws FieldParserException;

    protected String parse(final String source) {
        final String paddingString = String.valueOf(paddingCharacter);
        final String fieldString = source.substring(pos, pos + len);
        String fieldStringTrimmed = "";

        if (padding) {
            if (alignment.equals(AlignmentEnum.LEFT)) {
                fieldStringTrimmed = StringUtils.stripEnd(fieldString, paddingString);
            } else {
                fieldStringTrimmed = StringUtils.stripStart(fieldString, paddingString);
            }
        }
        return fieldStringTrimmed;
    }
}
