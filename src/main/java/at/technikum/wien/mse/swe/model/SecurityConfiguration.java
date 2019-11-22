package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityConfiguration {
    @IsinField(position = 40, length = 12, align = AlignmentEnum.LEFT, padding = false)
    private ISIN isin;

    @RiskCategoryField(position = 52)
    private RiskCategory riskCategory;

    @StringField(position = 54, length = 30, align = AlignmentEnum.RIGHT)
    private String name;

    @AmountField(positionBalance = 87, lengthBalance = 10, alignBalance = AlignmentEnum.RIGHT,
            positionCurrency = 84, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)
    private Amount yearHighest;

    @AmountField(positionBalance = 97, lengthBalance = 10, alignBalance = AlignmentEnum.RIGHT,
            positionCurrency = 84, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)
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


    @Override
    public String toString() {
        return "SecurityConfiguration{" +
                "isin='" + isin + '\'' +
                ", riskCategory=" + riskCategory +
                ", name=" + name +
                ", yearHighest=" + yearHighest +
                ", yearLowest=" + yearLowest + '}';
    }
}
