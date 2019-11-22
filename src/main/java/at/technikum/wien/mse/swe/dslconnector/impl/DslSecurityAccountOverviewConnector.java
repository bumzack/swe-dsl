package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.dslconnector.Parser;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DslSecurityAccountOverviewConnector implements SecurityAccountOverviewConnector {

    @Override
    public SecurityAccountOverview read(final Path file) {
        String content;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }

        final Parser parser = new Parser();

        try {
            final SecurityAccountOverview securityAccountOverview = parser.parse(content, SecurityAccountOverview.class);
            return securityAccountOverview;
        } catch (FieldParserException e) {
            System.out.println("Error parsing the string '" + content + "' \n  exception: " + e.getMessage());
        }

        return null;

    }
}
