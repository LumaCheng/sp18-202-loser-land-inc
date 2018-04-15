package com.loserland.context;

import com.loserland.worlds.MyWorld;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.InputStream;



public class PropertiesManager {

    private String resourceFileName;

    public PropertiesManager(String resourceFileName){
        this.resourceFileName = resourceFileName;
    }

    private InputStream getResourceAsStream(){
        return MyWorld.class.getClassLoader().getResourceAsStream(resourceFileName);
    }

//    public Object get(Class<?> cls, String key){
//        return configuration.get(cls, key);
//    }

    public String get(String key){
//        return configuration.getString(key);

        String value = null;

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(resourceFileName));
        try
        {
            Configuration config = builder.getConfiguration();

            value = config.get(String.class, key);
//            value = (String) get(String.class, "world.width");

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed
        }

        return value;
    }

    public void store(){

        System.out.println(get("colors.background"));

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(resourceFileName)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        Configuration config = null;
        try {
            config = builder.getConfiguration();
            config.setProperty("colors.background", "#000000");
            builder.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }


    }
}
