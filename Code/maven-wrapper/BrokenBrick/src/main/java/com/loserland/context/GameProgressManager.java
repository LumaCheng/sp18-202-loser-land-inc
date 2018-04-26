package com.loserland.context;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.configs.JsonSerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameProgressManager implements IGameProgressManager {

//    private Config progressStorage = ConfigFactory.getInstance().getConfig("game-progress-save.json");
    JsonSerializationUtils jsonSerializationUtils = new JsonSerializationUtils();

    private List<GameCheckPoint> checkPoints;
    private static GameProgressManager instance = null;

    private GameProgressManager(){
        checkPoints = new ArrayList<>();
    }

    public static GameProgressManager getInstance(){
        if (instance == null){
            synchronized (GameProgressManager.class){
                if (instance == null){
                    instance = new GameProgressManager();
                }
            }
        }
        return instance;
    }


    @Override
    public void add(GameCheckPoint checkPoint) {
        checkPoints.add(checkPoint);
        saveCheckPoint(checkPoint);
    }

    private void saveCheckPoint(GameCheckPoint checkPoint) {
        try {
            jsonSerializationUtils.objectToJson("configs/game-progress-save.json", checkPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public GameCheckPoint load() {
        if (checkPoints == null || checkPoints.size() == 0){
            try {
                return jsonSerializationUtils.jsonToObject("configs/game-progress-save.json", GameCheckPoint.class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return checkPoints.get(checkPoints.size() - 1);
    }
}
