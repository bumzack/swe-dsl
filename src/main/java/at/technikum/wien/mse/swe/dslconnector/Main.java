package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.impl.DslSecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.dslconnector.impl.DslSecurityConfigurationConnector;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;
import at.technikum.wien.mse.swe.model.SecurityConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String FILENAME = "examples/SecurityAccountOverview_0123456789.txt";
    private static final String FILENAME_CONFIGURATION = "examples/SecurityConfiguration_AT0000937503.txt";

    public static void main(String[] args) throws URISyntaxException {
        testOverview();
        testConfiguration();


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

        System.out.println("read line\n----------------------------------------------------------------------------------------------------------------");
        System.out.println(content);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println("\n\n\n object SecurityAccountOverview ");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println(overview);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
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

        System.out.println("read line\n----------------------------------------------------------------------------------------------------------------");
        System.out.println(content);
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        System.out.println("\n\n\n object SecurityConfiguration ");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println(configuration);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }
}
