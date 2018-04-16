package com.loserland.context;

public class GameConfigFactory {
    private static GameConfigFactory instance = null;

    private GameConfigFactory(){ }

    public static GameConfigFactory getInstance(){
        if (instance == null){
            synchronized (GameConfigFactory.class){
                if (instance == null){
                    instance = new GameConfigFactory();
                }
            }
        }
        return instance;
    }
    public GameConfig getConfig(String fileName){

        if (fileName.endsWith(".properties")){
            return new PropertiesGameConfig(fileName);
        } else if(fileName.endsWith(".json")){
            return new JsonGameConfig(fileName);
        } else { //by default, load config as .properties
            return new PropertiesGameConfig(fileName);
        }
    }
}
