package com.loserland.context;

import com.loserland.worlds.MyWorld;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class PropertiesManager {

    private String resourceName;

    public PropertiesManager(String resourceName){
        this.resourceName = resourceName;
    }

    public String get(String key){
        String value = null;
        Properties properties = new Properties();
        InputStream inputStream = MyWorld.class.getClassLoader().getResourceAsStream(resourceName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
