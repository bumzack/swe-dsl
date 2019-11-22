package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.RiskCategoryField;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

/**
 * @author MatthiasKreuzriegler
 */

public class SecurityAccountOverview {

    @SimpleElement(position = 40, length = 10, align = AlignmentEnum.RIGHT, paddingCharacter = '0')
    private String accountNumber;

    @RiskCategoryField(position = 50)
    private RiskCategory riskCategory;

    @ComplexElement(name = {"lastname", "firstname"}, position = {52, 82}, length = {30, 30}, align = {AlignmentEnum.RIGHT, AlignmentEnum.RIGHT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private DepotOwner depotOwner;

    @ComplexElement(name = {"currency", "value"}, position = {112, 115}, length = {3, 17}, align = {AlignmentEnum.RIGHT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private Amount balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public DepotOwner getDepotOwner() {
        return depotOwner;
    }

    public void setDepotOwner(DepotOwner depotOwner) {
        this.depotOwner = depotOwner;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "SecurityAccountOverview{" +
                "accountNumber='" + accountNumber + '\'' +
                ", riskCategory=" + riskCategory +
                ", depotOwner=" + depotOwner +
                ", balance=" + balance +
                '}';
    }
}
