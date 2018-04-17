package com.loserland.configs;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public abstract class AbstractConfig implements Config {

    protected FileBasedConfigurationBuilder builder;
    protected String resourceFileName;
    protected Parameters params;

    protected AbstractConfig(String resourceFileName){
        this.resourceFileName = resourceFileName;
        this.params = new Parameters();
    }

    @Override
    public String get(String key) {
        return get(String.class, key);
    }

    @Override
    public <T> T get(Class<T> cls, String key) {
        T value = null;
        try {
            value = getConfiguration().get(cls, key);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void put(String key, Object value) {
        try {
            getConfiguration().setProperty(key, value);
            getBuilder().save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    protected abstract FileBasedConfigurationBuilder getBuilder();

    protected abstract Configuration getConfiguration() throws ConfigurationException;
}
