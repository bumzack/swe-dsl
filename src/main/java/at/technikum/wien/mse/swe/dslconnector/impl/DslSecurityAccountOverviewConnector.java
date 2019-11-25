package at.technikum.wien.mse.swe.dslconnector.impl;

import at.technikum.wien.mse.swe.SecurityAccountOverviewConnector;
import at.technikum.wien.mse.swe.dslconnector.GenericMapper;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.model.SecurityAccountOverview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class DslSecurityAccountOverviewConnector implements SecurityAccountOverviewConnector {
    private static final Logger LOG = LogManager.getLogger(DslSecurityConfigurationConnector.class);

    @Override
    public SecurityAccountOverview read(final Path file) {
        final String content = FileHelper.readStringFromFile(file);

        final GenericMapper genericMapper = new GenericMapper();

        try {
            return genericMapper.map(content, SecurityAccountOverview.class);
        } catch (FieldMapperException e) {
            LOG.error("Error parsing the string '" + content + "'   exception: " + e.getMessage());
        }

        return null;
    }
}
