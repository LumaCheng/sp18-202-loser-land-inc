package com.loserland.configs;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
    public List<String> getList(String key) {
        return getList(String.class, key);
    }

    @Override
    public <T> List<T> getList(Class<T> cls, String key) {
        List<T> list = null;
        try {
            list = getConfiguration().getList(cls, key);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return list;
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
