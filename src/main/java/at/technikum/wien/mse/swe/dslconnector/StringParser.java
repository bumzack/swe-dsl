package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;

public class StringParser extends AbstractFieldParser<String> {

    public StringParser(int pos, int len, AlignmentEnum alignment, char padding) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
    }

    public String parseValue(final String source) {
        return parse(source);
    }
}
