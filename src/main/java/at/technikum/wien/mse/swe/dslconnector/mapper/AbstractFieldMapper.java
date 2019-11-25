package at.technikum.wien.mse.swe.dslconnector.mapper;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.dslconnector.mapper.dto.SimpleTypeDto;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractFieldMapper implements FieldMapper {

    protected SimpleTypeDto simpleTypeDto;

    @Override
    public abstract <T> T mapValue(final String source) throws FieldMapperException;

    protected String parseString(final String source) {
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
