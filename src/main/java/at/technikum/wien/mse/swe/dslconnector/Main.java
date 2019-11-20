package at.technikum.wien.mse.swe.dslconnector;

public class Main {
    public static void main(String[] args) {
        final Parser p = new Parser();

        try {
            p.parse();
        } catch (FieldParserException e) {
            System.out.println("caught exception: " + e);
        }
    }
}
