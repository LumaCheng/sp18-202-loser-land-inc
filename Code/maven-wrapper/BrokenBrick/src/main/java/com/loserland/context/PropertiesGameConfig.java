package com.loserland.context;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesGameConfig implements GameConfig {

    private FileBasedConfigurationBuilder<PropertiesConfiguration> builder;
    private String resourceFileName;
    private Parameters params;

    protected PropertiesGameConfig(String resourceFileName){
        this.resourceFileName = resourceFileName;
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

    public FileBasedConfigurationBuilder<PropertiesConfiguration> getBuilder() {
        if (builder == null){
            builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                    .configure(params.properties()
                            .setFileName(resourceFileName));

        }
        return builder;
    }
}
