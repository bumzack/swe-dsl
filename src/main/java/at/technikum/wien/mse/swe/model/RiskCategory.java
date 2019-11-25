package at.technikum.wien.mse.swe.model;

/**
 * @author MatthiasKreuzriegler
 */
public enum RiskCategory {

    NON_EXISTING("00"),
    EXECUTION_ONLY("01"),
    LOW("02"),
    MIDDLE("04"),
    HIGH("06"),
    SPECULATIVE("08");

    private final String code;

    RiskCategory(String code) {
        this.code = code;
    }
}
