package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.parser.AbstractFieldParser;

public class StringParser extends AbstractFieldParser {

    public StringParser(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingChar) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = true;
        this.paddingCharacter = paddingChar;
    }

    @Override
    public String parseValue(final String source) {
        return parse(source);
    }
}