package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.model.RiskCategory;
import org.apache.commons.lang.StringUtils;

public class RiskCategoryParser implements FieldParser {
    private StringParser stringParser;

    private int pos;
    private int len;

    public RiskCategoryParser(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        this.len = len;
        this.pos = pos;
        stringParser = new StringParser(pos, len, alignment, padding, paddingCharacter);
    }

    public RiskCategory parseValue(final String source) throws FieldParserException {
        final String enumValue = stringParser.parseValue(source);
        System.out.println("");
        if (StringUtils.isEmpty(enumValue)) {
            return null;
        }
        return RiskCategory.fromCode(enumValue).orElseThrow(() -> new FieldParserException("cant read enum at field with position " + pos + ", len: " + len + " in source string '" + source + "'"));
    }
}
