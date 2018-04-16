package com.loserland.context;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;

public class PropertiesConfigurationManager extends ConfigurationManagerFactory {

//    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    protected PropertiesConfigurationManager(String resourceFileName) {
        super(resourceFileName);
    }


    public static ConfigurationManagerFactory getInstance(String resourceFileName){
        return createInstance(resourceFileName);
//        if (instance == null){
//            synchronized (ConfigurationManagerFactory.class){
//                if(instance == null){
//                    instance = createInstance(resourceFileName);
//                }
//            }
//        }
//        return instance;
    }

    @Override
    protected FileBasedConfigurationBuilder<FileBasedConfiguration> createBuilder() {
        builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);
        return builder;
    }


}
