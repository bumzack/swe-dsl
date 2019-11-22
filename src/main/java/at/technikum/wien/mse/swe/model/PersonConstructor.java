package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

import java.math.BigDecimal;

/**
 * @author MatthiasKreuzriegler
 */

public class PersonConstructor {

    @SimpleElement(position = 0, length = 10, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private String firstName;

    @SimpleElement(position = 10, length = 10, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private String lastName;

    @SimpleElement(position = 10, length = 5, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private BigDecimal bigDeci;


    @ComplexElement(name = {"currency", "value"}, position = {15, 20}, length = {3, 5}, align = {AlignmentEnum.RIGHT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private Amount balance;

    public PersonConstructor(String firstName, String lastName, BigDecimal bigDeci, Amount balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bigDeci = bigDeci;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", firstname=" + firstName +
                ", lastName=" + lastName +
                ", bigDeci=" + bigDeci +
                ", balance=" + balance +
                '}';
    }
}
