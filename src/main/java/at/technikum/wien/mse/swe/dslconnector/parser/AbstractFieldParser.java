package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.dto.SimpleTypeDto;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractFieldParser implements FieldParser {

    protected SimpleTypeDto simpleTypeDto;

    @Override
    public abstract <T> T parseValue(final String source) throws FieldParserException;

    protected String parse(final String source) {
        final String paddingString = String.valueOf(simpleTypeDto.getLen());
        final String fieldString = source.substring(simpleTypeDto.getPos(), simpleTypeDto.getPos() + simpleTypeDto.getLen());
        String fieldStringTrimmed = "";

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
