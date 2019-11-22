package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityConfigurationConnector;
import at.technikum.wien.mse.swe.dslconnector.Parser;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DslSecurityConfigurationConnector implements SecurityConfigurationConnector {
    private static final Logger LOG = LogManager.getLogger(DslSecurityConfigurationConnector.class);

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
            LOG.error("Error parsing the string '" + content + "'     exception: " + e.getMessage());
        }

        return null;
    }
}
