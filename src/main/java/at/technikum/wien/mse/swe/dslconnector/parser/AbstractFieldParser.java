package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.dto.SimpleTypeDto;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractFieldParser implements FieldParser {

    protected SimpleTypeDto simpleTypeDto;

    @Override
    public abstract <T> T parseValue(final String source) throws FieldParserException;

    protected String parse(final String source) {
        final String paddingString = String.valueOf(simpleTypeDto.getPaddingCharacter());
        final String fieldString = source.substring(simpleTypeDto.getPos(), simpleTypeDto.getPos() + simpleTypeDto.getLen());
        String fieldStringTrimmed = fieldString;

        if (simpleTypeDto.isPadding()) {
            if (simpleTypeDto.getAlignment().equals(AlignmentEnum.LEFT)) {
                fieldStringTrimmed = StringUtils.stripEnd(fieldString, paddingString);
            } else {
                fieldStringTrimmed = StringUtils.stripStart(fieldString, paddingString);
            }
        }
        return fieldStringTrimmed;
    }
}
