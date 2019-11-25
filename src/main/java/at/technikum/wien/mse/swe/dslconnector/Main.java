package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.dslconnector.model.PersonConstructor;
import at.technikum.wien.mse.swe.dslconnector.model.PersonSetter;
import at.technikum.wien.mse.swe.dslconnector.model.PersonWithEnumAndIntegerAndLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    private static final String FILENAME = "examples/SecurityAccountOverview_0123456789.txt";
    private static final String FILENAME_CONFIGURATION = "examples/SecurityConfiguration_AT0000937503.txt";

    public static void main(String[] args) throws FieldMapperException, URISyntaxException {
        testEnum();
        testPerson();
    }

    private static void testEnum() throws FieldMapperException {
        final String source = "Maxi Muxi 13.12EUR  23.451234a     unknown                            Street                        Stiege 1/3     04      2312345      ";

        final GenericMapper p = new GenericMapper();

        final PersonWithEnumAndIntegerAndLong enumTest = p.map(source, PersonWithEnumAndIntegerAndLong.class);

        LOG.trace("");
        LOG.trace("");
        LOG.trace("------ source ----------------------------------------------------------------------------------------------------------");
        LOG.trace(source);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");

        LOG.trace("");
        LOG.trace("");
        LOG.trace("----------object EnumTest   ------------------------------------------------------------------------------------------------------");
        LOG.trace(enumTest);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");
    }

    private static void testPerson() throws FieldMapperException {
        final String source = "Maxi Muxi 13.12EUR  23.451234a     unknown                            Street                        Stiege 1/3    03   ";

        final GenericMapper p = new GenericMapper();

        final PersonSetter personSetter = p.map(source, PersonSetter.class);

        LOG.trace("");
        LOG.trace("");
        LOG.trace("------ source ----------------------------------------------------------------------------------------------------------");
        LOG.trace(source);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");

        LOG.trace("");
        LOG.trace("");
        LOG.trace("----------object PersonSetter   ------------------------------------------------------------------------------------------------------");
        LOG.trace(personSetter);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");

        final PersonConstructor personConstructor = p.map(source, PersonConstructor.class);
        LOG.trace("");
        LOG.trace("");
        LOG.trace("-----------   object PersonConstructor   -----------------------------------------------------------------------------------------------------");
        LOG.trace(personConstructor);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");
    }
}
