package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.BigDecimalField;
import at.technikum.wien.mse.swe.dslconnector.annotations.RiskCategoryField;
import at.technikum.wien.mse.swe.dslconnector.annotations.StringField;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityConfiguration {
    @StringField(position = 52, length = 12, align = AlignmentEnum.RIGHT, padding = false)
    private ISIN isin;

    @RiskCategoryField(position = 50)
    private RiskCategory riskCategory;

    @StringField(position = 54, length = 30, align = AlignmentEnum.RIGHT)
    private String name;

    @BigDecimalField(position = 87, length = 10)
    private Amount yearHighest;

    @BigDecimalField(position = 97, length = 10)
    private Amount yearLowest;

    public ISIN getIsin() {
        return isin;
    }

    public void setIsin(ISIN isin) {
        this.isin = isin;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Amount getYearHighest() {
        return yearHighest;
    }

    public void setYearHighest(Amount yearHighest) {
        this.yearHighest = yearHighest;
    }

    public Amount getYearLowest() {
        return yearLowest;
    }

    public void setYearLowest(Amount yearLowest) {
        this.yearLowest = yearLowest;
    }
}
