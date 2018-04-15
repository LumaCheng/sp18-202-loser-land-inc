package com.loserland.context;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


public abstract class ConfigurationManagerFactory {

    private static ConfigurationManagerFactory instance = null;
    private String resourceFileName;
    protected Parameters params;
    protected FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    protected ConfigurationManagerFactory(String resourceFileName){
        this.resourceFileName = resourceFileName;
        this.params = new Parameters();
    }

    public static ConfigurationManagerFactory getInstance(String resourceFileName){
        if (instance == null){
            synchronized (ConfigurationManagerFactory.class){
                if(instance == null){
                    instance = createInstance(resourceFileName);
                }
            }
        }
        return instance;
    }

    private static ConfigurationManagerFactory createInstance(String resourceFileName) {
        if (resourceFileName.endsWith(".properties")){
            return new PropertiesConfigurationManager(resourceFileName);
        } else{
            return new PropertiesConfigurationManager(resourceFileName);
        }
    }


    private FileBasedConfigurationBuilder<FileBasedConfiguration> getBuilder(){
//        if (builder == null) {
//            synchronized (this.getClass()) {
//                if (builder == null) {
//                    builder = createBuilder();
//                }
//            }
//        }
        builder = createBuilder();
        initBuilder(builder);
        return builder;
    }

    protected abstract FileBasedConfigurationBuilder<FileBasedConfiguration> createBuilder();

    private void initBuilder(FileBasedConfigurationBuilder builder) {
        builder.configure(params.properties()
                .setFileName(resourceFileName));
    }

    public String get(String key){
        return get(String.class, key);
    }

    public <T> T get(Class<T> cls, String key){
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

//    public Object get(Class<?> cls, String key){
//        Configuration configuration = null;
//        try
//        {
//            configuration = getBuilder().getConfiguration();
//        }
//        catch(ConfigurationException cex)
//        {
//            // loading of the configuration file failed
//        }
//        return configuration.get(cls, key);
//    }

    public void put(String key, Object value){
        try {
            Configuration configuration = getBuilder().getConfiguration();
            configuration.setProperty(key, value);
            getBuilder().save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
