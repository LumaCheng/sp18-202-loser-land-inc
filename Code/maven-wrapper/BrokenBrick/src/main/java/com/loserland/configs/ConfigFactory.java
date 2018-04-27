package com.loserland.configs;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
    private static ConfigFactory instance = null;
    private Map<String, Config> configMap;

    private ConfigFactory(){
        configMap = new HashMap<>();
    }

    public static ConfigFactory getInstance(){
        if (instance == null){
            synchronized (ConfigFactory.class){
                if (instance == null){
                    instance = new ConfigFactory();
                }
            }
        }
        return instance;
    }

    public Config getConfig(String fileName){
        Config config = null;
        if (configMap.containsKey(fileName)){
            config = configMap.get(fileName);
        } else{
            if(fileName.endsWith(".properties")){
                config = new PropertiesConfig(fileName);
            } else if(fileName.endsWith(".json")){
                config = new JsonConfig(fileName);
            } else { //by default, load config as .properties
                config = new PropertiesConfig(fileName);
            }
            configMap.put(fileName, config);
        }
        return config;
    }
}
