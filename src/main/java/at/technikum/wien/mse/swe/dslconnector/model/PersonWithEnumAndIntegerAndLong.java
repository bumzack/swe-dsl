package at.technikum.wien.mse.swe.dslconnector.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

/**
 * @author gs
 */

public class PersonWithEnumAndIntegerAndLong {

    @SimpleElement(position = 115, length = 2, padding = false, align = AlignmentEnum.LEFT)
    private EnumTest enumtest;

    @SimpleElement(position = 120, length = 5, padding = true, align = AlignmentEnum.RIGHT, paddingCharacter = ' ')
    private Integer i;

    @SimpleElement(position = 125, length = 5, padding = true, align = AlignmentEnum.RIGHT)
    private Long l;

    public void setEnumtest(EnumTest enumtest) {
        this.enumtest = enumtest;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setL(Long l) {
        this.l = l;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", enumtest= '" + enumtest +
                ", i= '" + i +
                ", l= '" + l + "' }";
    }
}
