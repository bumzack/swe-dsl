package at.technikum.wien.mse.swe.model;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;

import java.math.BigDecimal;

/**
 * @author gs
 */

public class PersonSetter {

    @SimpleElement(position = 0, length = 5, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private String firstName;

    @SimpleElement(position = 5, length = 5, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private String lastName;

    @SimpleElement(position = 10, length = 5, align = AlignmentEnum.LEFT, paddingCharacter = ' ')
    private BigDecimal bigDeci;

    @ComplexElement(name = {"currency", "value"}, position = {15, 20}, length = {3, 5}, align = {AlignmentEnum.RIGHT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private Amount balance;

    @ComplexElement(name = {"zip", "town"}, position = {25, 35}, length = {4, 35}, align = {AlignmentEnum.LEFT, AlignmentEnum.LEFT},
            padding = {true, true}, paddingCharacter = {' ', ' '})
    private Address address;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBigDeci(BigDecimal bigDeci) {
        this.bigDeci = bigDeci;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", firstname=" + firstName +
                ", lastName=" + lastName +
                ", bigDeci=" + bigDeci +
                ", balance=" + balance +
                ", address=" + address +
                '}';
    }
}
