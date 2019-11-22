package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.impl.DslSecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.dslconnector.impl.DslSecurityConfigurationConnector;
import at.technikum.wien.mse.swe.dslconnector.model.PersonConstructor;
import at.technikum.wien.mse.swe.dslconnector.model.PersonSetter;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;
import at.technikum.wien.mse.swe.model.SecurityConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    private static final String FILENAME = "examples/SecurityAccountOverview_0123456789.txt";
    private static final String FILENAME_CONFIGURATION = "examples/SecurityConfiguration_AT0000937503.txt";

    public static void main(String[] args) throws FieldParserException, URISyntaxException {
        testPerson();
        testOverview();
        testConfiguration();
    }

    private static void testPerson() throws FieldParserException {
        final String source = "Maxi Muxi 13.12EUR  23.451234a     unknown                            Street                        Stiege 1/3 ";

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

    private static void testOverview() throws URISyntaxException {
        final DslSecurityAccountOverviewConnector p = new DslSecurityAccountOverviewConnector();

        String content = "";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(FILENAME).toURI()))) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }

        SecurityAccountOverview overview = p.read(Paths.get(ClassLoader.getSystemResource(FILENAME).toURI()));
        LOG.trace("");
        LOG.trace("");
        LOG.trace("--------  read line ----------------------------------------------------------------------------------------------------------------");
        LOG.trace(content);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");

        LOG.trace("");
        LOG.trace("");
        LOG.trace("----------- object SecurityAccountOverview  -----------------------------------------------------------------------------------------------------");
        LOG.trace(overview);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");
    }

    private static void testConfiguration() throws URISyntaxException {
        final DslSecurityConfigurationConnector p = new DslSecurityConfigurationConnector();

        String content = "";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(FILENAME_CONFIGURATION).toURI()))) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }

        SecurityConfiguration configuration = p.read(Paths.get(ClassLoader.getSystemResource(FILENAME_CONFIGURATION).toURI()));
        LOG.trace("");
        LOG.trace("");
        LOG.trace("-------    read line   ----------------------------------------------------------------------------------------------------------------");
        LOG.trace(content);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");

        LOG.trace("");
        LOG.trace("");
        LOG.trace("---------- object SecurityConfiguration  ------------------------------------------------------------------------------------------------------");
        LOG.trace(configuration);
        LOG.trace("----------------------------------------------------------------------------------------------------------------");
    }
}
