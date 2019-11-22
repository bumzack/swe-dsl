package at.technikum.wien.mse.swe.dslconnector.parser.dto;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;

public class ComplexTypeDto {
    private String name;
    private int pos;
    private int len;
    private AlignmentEnum alignment;
    private boolean padding;
    private char paddingCharacter;


    public ComplexTypeDto(String name, int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        this.name = name;
        this.pos = pos;
        this.len = len;
        this.alignment = alignment;
        this.padding = padding;
        this.paddingCharacter = paddingCharacter;
    }

    static public ComplexTypeDto map(String name, int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        return new ComplexTypeDto(name, pos, len, alignment, padding, paddingCharacter);
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

    public String getName() {
        return name;
    }

}
