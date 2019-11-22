package at.technikum.wien.mse.swe.dslconnector;

import at.technikum.wien.mse.swe.dslconnector.impl.DslSecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String FILENAME = "examples/SecurityAccountOverview_0123456789.txt";


    public static void main(String[] args) throws URISyntaxException {
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
}
