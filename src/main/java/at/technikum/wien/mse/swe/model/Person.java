package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.AmountField;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

/**
 * @author MatthiasKreuzriegler
 */

public class Person {

    @SimpleElement(position = 0, length = 10, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private String firstName;

    @AmountField(positionBalance = 15, lengthBalance = 10, alignBalance = AlignmentEnum.RIGHT,
            positionCurrency = 25, lengthCurrency = 3, alignCurrency = AlignmentEnum.LEFT)
    private Amount balance;

    public Person(String firstName, Amount balance) {
        this.firstName = firstName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
