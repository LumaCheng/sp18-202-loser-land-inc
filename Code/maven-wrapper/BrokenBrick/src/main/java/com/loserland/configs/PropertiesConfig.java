package com.loserland.configs;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesConfig extends AbstractConfig {

    public PropertiesConfig(String resourceFileName) {
        super(resourceFileName);
    }

    public FileBasedConfigurationBuilder<PropertiesConfiguration> getBuilder() {
        builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                        .setFileName(resourceFileName));
        return builder;
    }

    @Override
    protected Configuration getConfiguration() throws ConfigurationException {
        return getBuilder().getConfiguration();
    }
}
