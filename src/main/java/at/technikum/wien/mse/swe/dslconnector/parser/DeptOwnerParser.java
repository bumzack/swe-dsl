package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.model.DepotOwner;

public class DeptOwnerParser extends AbstractFieldParser {

    private int lenLastName;
    private int lenFirstName;
    private int position;

    public DeptOwnerParser(int pos, int lenFirstName, int lenLastName, AlignmentEnum alignment, char padding) {
        this.position = pos;
        this.len = lenFirstName;
        this.alignment = alignment;
        this.padding = padding;

        this.lenLastName = lenLastName;
    }

    public DepotOwner parseValue(final String source) {
        this.pos = position;
        this.len = lenFirstName;
        final String firstName = parse(source);

        this.pos += this.len;
        this.len = lenLastName;

        final String lastName = parse(source);
        final DepotOwner depotOwner = new DepotOwner();
        depotOwner.setFirstname(firstName);
        depotOwner.setLastname(lastName);
        return depotOwner;
    }
}
