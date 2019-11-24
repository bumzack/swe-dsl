package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityConfigurationConnector;
import at.technikum.wien.mse.swe.dslconnector.GenericMapper;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
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

        final GenericMapper genericMapper = new GenericMapper();

        try {
            final SecurityConfiguration securityConfiguration = genericMapper.map(content, SecurityConfiguration.class);
            return securityConfiguration;
        } catch (FieldMapperException e) {
            LOG.error("Error parsing the string '" + content + "'     exception: " + e.getMessage());
        }

        return null;
    }
}
