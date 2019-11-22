package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.model.RiskCategory;

public class RiskCategoryParser extends AbstractFieldParser {

    public RiskCategoryParser(int pos, int len, AlignmentEnum alignment, char padding) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
    }

    public RiskCategory parseValue(final String source) {
        //T ODO:
        // return parse(source);
        return RiskCategory.EXECUTION_ONLY;
    }
}
