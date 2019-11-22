package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.parser.FieldParser;
import at.technikum.wien.mse.swe.model.DepotOwner;

public class DeptOwnerParser implements FieldParser {

    private StringParser firstNameParser;
    private StringParser lastNameParser;

    public DeptOwnerParser(int pos, int lenFirstName, int lenLastName, AlignmentEnum alignment, boolean padding, char paddingChar) {
        lastNameParser = new StringParser(pos, lenLastName, alignment, padding, paddingChar);
        firstNameParser = new StringParser(pos + lenLastName, lenFirstName, alignment, padding, paddingChar);
    }

    public DepotOwner parseValue(final String source) {
        final String firstName = firstNameParser.parseValue(source);
        final String lastName = lastNameParser.parseValue(source);
        final DepotOwner depotOwner = new DepotOwner();
        depotOwner.setFirstname(firstName);
        depotOwner.setLastname(lastName);
        return depotOwner;
    }
}
