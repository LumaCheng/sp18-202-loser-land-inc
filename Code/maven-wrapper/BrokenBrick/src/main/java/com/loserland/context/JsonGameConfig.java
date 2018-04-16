package com.loserland.context;

import com.virtlink.commons.configuration2.jackson.JsonConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class JsonGameConfig implements GameConfig {

    private FileBasedConfigurationBuilder builder;
    private String fileName;
    private Parameters params;

    protected JsonGameConfig(String fileName){
        this.fileName = fileName;
        this.params = new Parameters();
    }

    @Override
    public String get(String key) {
        return get(String.class, key);
    }

    @Override
    public <T> T get(Class<T> cls, String key) {
        Configuration configuration = null;
        try
        {

            configuration = getBuilder().getConfiguration();
        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed
        }
        return configuration.get(cls, key);
    }

    @Override
    public void put(String key, Object value) {
        try {
            Configuration configuration = getBuilder().getConfiguration();
            configuration.setProperty(key, value);
            getBuilder().save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Configuration getConfiguration() throws ConfigurationException {
        return getBuilder().getConfiguration();
    }

    private FileBasedConfigurationBuilder<PropertiesConfiguration> getBuilder() {
        if (builder == null){
            builder
                    = new FileBasedConfigurationBuilder<>(JsonConfiguration.class).configure(params.fileBased()
                    .setFileName(fileName));


//            builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
//                    .configure(params.properties()
//                            .setFileName(fileName));


//            FileBasedConfigurationBuilder<JsonConfiguration> builder
//                    = new FileBasedConfigurationBuilder<>(JsonConfiguration.class);
//            JsonConfiguration config = null;
//            config = builder.configure(params
//                    .fileBased()
//                    .setFileName(fileName)).getConfiguration();

        }
        return builder;
    }
}
