package com.loserland.configs;

public class ConfigFactory {
    private static ConfigFactory instance = null;

    private ConfigFactory(){ }

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

        if (fileName.endsWith(".properties")){
            return new PropertiesConfig(fileName);
        } else if(fileName.endsWith(".json")){
            return new JsonConfig(fileName);
        } else { //by default, load config as .properties
            return new PropertiesConfig(fileName);
        }
    }
}
