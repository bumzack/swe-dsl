package at.technikum.wien.mse.swe.dslconnector.mapper.dto;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

public class SimpleTypeDto {
    private int pos;
    private int len;
    private AlignmentEnum alignment;
    private boolean padding;
    private char paddingCharacter;


    public SimpleTypeDto(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
        this.paddingCharacter = paddingCharacter;
    }

    static public SimpleTypeDto map(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        return new SimpleTypeDto(pos, len, alignment, padding, paddingCharacter);
    }

    static public SimpleTypeDto map(final ComplexTypeDto complexTypeDto) {
        return new SimpleTypeDto(complexTypeDto.getPos(), complexTypeDto.getLen(), complexTypeDto.getAlignment(), complexTypeDto.isPadding(), complexTypeDto.getPaddingCharacter());
    }

    static public SimpleTypeDto map(final SimpleElement simpleElement) {
        return new SimpleTypeDto(simpleElement.position(), simpleElement.length(), simpleElement.align(), simpleElement.padding(), simpleElement.paddingCharacter());
    }

    public int getPos() {
        return pos;
    }

    public int getLen() {
        return len;
    }

    public AlignmentEnum getAlignment() {
        return alignment;
    }

    public boolean isPadding() {
        return padding;
    }

    public char getPaddingCharacter() {
        return paddingCharacter;
    }
}
