package com.loserland.context;

import com.virtlink.commons.configuration2.jackson.JsonConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class JsonConfigManager {
    private String fileName;

    public JsonConfigManager(String fileName){
        this.fileName = fileName;
    }

    private  JsonConfiguration getConfig(){
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<JsonConfiguration> builder
                = new FileBasedConfigurationBuilder<>(JsonConfiguration.class);
        JsonConfiguration config = null;
        try {
             config = builder.configure(params
                    .fileBased()
                    .setFileName(fileName)).getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }




}
