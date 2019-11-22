package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.dslconnector.GenericMapper;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DslSecurityAccountOverviewConnector implements SecurityAccountOverviewConnector {
    private static final Logger LOG = LogManager.getLogger(DslSecurityConfigurationConnector.class);

    @Override
    public SecurityAccountOverview read(final Path file) {
        String content;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new SecurityAccountOverviewReadException(e);
        }

        final GenericMapper genericMapper = new GenericMapper();

        try {
            final SecurityAccountOverview securityAccountOverview = genericMapper.map(content, SecurityAccountOverview.class);
            return securityAccountOverview;
        } catch (FieldParserException e) {
            LOG.error("Error parsing the string '" + content + "'   exception: " + e.getMessage());
        }

        return null;
    }
}
