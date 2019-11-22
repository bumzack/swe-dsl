package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityConfigurationConnector;
import at.technikum.wien.mse.swe.dslconnector.Parser;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DslSecurityConfigurationConnector implements SecurityConfigurationConnector {

    @Override
    public SecurityConfiguration read(final Path file) {
        String content;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }

        final Parser parser = new Parser();

        try {
            final SecurityConfiguration securityConfiguration = parser.parse(content, SecurityConfiguration.class);
            return securityConfiguration;
        } catch (FieldParserException e) {
            System.out.println("Error parsing the string '" + content + "'");
        }

        return null;
    }
}
