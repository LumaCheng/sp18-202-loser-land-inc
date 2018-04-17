package com.loserland.configs;

import com.virtlink.commons.configuration2.jackson.JacksonConfiguration;
import com.virtlink.commons.configuration2.jackson.JsonConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class JsonConfig extends AbstractConfig {

    public JsonConfig(String resourceFileName) {
        super(resourceFileName);
    }

    protected JacksonConfiguration getConfiguration() throws ConfigurationException {
        return getBuilder().getConfiguration();
    }

    protected FileBasedConfigurationBuilder<JsonConfiguration> getBuilder() {
        builder
                = new FileBasedConfigurationBuilder<>(JsonConfiguration.class).configure(params.fileBased()
                .setFileName(resourceFileName));
        return builder;
    }

    @Override
    public String get(String key) {
        try {
            return getConfiguration().getString(key);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

}
