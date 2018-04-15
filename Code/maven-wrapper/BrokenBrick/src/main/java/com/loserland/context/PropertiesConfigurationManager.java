package com.loserland.context;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;

public class PropertiesConfigurationManager extends ConfigurationManagerFactory {

//    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    protected PropertiesConfigurationManager(String resourceFileName) {
        super(resourceFileName);
    }


    @Override
    protected FileBasedConfigurationBuilder<FileBasedConfiguration> createBuilder() {
        builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);
        return builder;
    }


}
