package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

/**
 * @author MatthiasKreuzriegler
 */
public class SecurityConfiguration {
    //   @IsinField(position = 40, length = 12, align = AlignmentEnum.LEFT, padding = false)

    @ComplexElement(name = {"value"}, position = {40}, length = {12}, align = {AlignmentEnum.LEFT},
            padding = {false}, paddingCharacter = {' '})
    private ISIN isin;

    @ComplexElement(name = {"code"}, position = {52}, length = {2}, align = {AlignmentEnum.LEFT}, padding = {false}, paddingCharacter = {' '})
    private RiskCategory riskCategory;

    @SimpleElement(position = 54, length = 30, align = AlignmentEnum.RIGHT)
    private String name;

//    @AmountField(positionBalance = 87, lengthBalance = 10, alignBalance = AlignmentEnum.RIGHT,
//            positionCurrency = 84, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)

    @ComplexElement(name = {"currency", "value"}, position = {84, 87}, length = {3, 10}, align = {AlignmentEnum.RIGHT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private Amount yearHighest;

    //    @AmountField(positionBalance = 97, lengthBalance = 10, alignBalance = AlignmentEnum.RIGHT,
    //        positionCurrency = 84, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)

    @ComplexElement(name = {"currency", "value"}, position = {84, 97}, length = {3, 10}, align = {AlignmentEnum.RIGHT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
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
