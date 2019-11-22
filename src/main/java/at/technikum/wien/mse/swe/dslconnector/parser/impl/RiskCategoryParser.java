package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.AbstractFieldParser;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.dto.SimpleTypeDto;
import at.technikum.wien.mse.swe.model.RiskCategory;
import org.apache.commons.lang.StringUtils;

public class RiskCategoryParser extends AbstractFieldParser {

    public RiskCategoryParser(final SimpleTypeDto simpleTypeDto) {
        this.simpleTypeDto = simpleTypeDto;
    }

    public RiskCategory parseValue(final String source) throws FieldParserException {
        final String enumValue = parse(source);
        System.out.println("");
        if (StringUtils.isEmpty(enumValue)) {
            return null;
        }
        return RiskCategory.fromCode(enumValue).orElseThrow(() -> new FieldParserException("can't read enum at field with position " + simpleTypeDto.getPos() + ", len: " + simpleTypeDto.getLen() + " in source string '" + source + "'"));
    }
}

