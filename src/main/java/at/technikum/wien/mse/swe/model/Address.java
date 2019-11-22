package at.technikum.wien.mse.swe.model;

/**
 * @author gs
 */

public class Address {

    private String street;
    private String number;
    private String town;
    private String zip;

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address {" +
                " street= '" + street + "'" +
                ", number= '" + number + "'" +
                ", town= '" + town + "'" +
                ", zip= '" + zip + "'" +
                '}';
    }
}
