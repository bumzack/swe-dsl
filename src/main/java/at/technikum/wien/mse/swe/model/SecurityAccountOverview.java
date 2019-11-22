package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;

/**
 * @author MatthiasKreuzriegler
 */

public class SecurityAccountOverview {

    @SimpleElement(position = 40, length = 10, align = AlignmentEnum.RIGHT, paddingCharacter = '0')
    private String accountNumber;

    @RiskCategoryField(position = 50)
    private RiskCategory riskCategory;

    @DepotOwnerField(position = 52, lengthFirstName = 30, lengthLastName = 30, padding = true, paddingChar = ' ', align = AlignmentEnum.RIGHT)
    private DepotOwner depotOwner;

    @AmountField(positionBalance = 115, lengthBalance = 17, alignBalance = AlignmentEnum.RIGHT,
            positionCurrency = 112, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)
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
