package at.technikum.wien.mse.swe.dslconnector.model;

import java.util.Arrays;
import java.util.Optional;

public enum EnumTest {
    VIENAA("00"),
    LOWERAUSTRIA("01"),
    UPPERAUSTRIA("02"),
    STYRIA("04"),
    TYROL("06");

    private final String code;

    EnumTest(String code) {
        this.code = code;
    }

    // test if the mapper finds this method
    public static final Optional<EnumTest> justDoIt(String code) {
        return Arrays.stream(values()).filter(rc -> rc.code.equalsIgnoreCase(code)).findFirst();
    }
}
