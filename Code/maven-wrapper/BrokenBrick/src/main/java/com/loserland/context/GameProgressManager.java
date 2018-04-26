package com.loserland.context;

import java.util.ArrayList;
import java.util.List;

public class GameProgressManager implements IGameProgressManager {

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
    }

    @Override
    public GameCheckPoint load() {
        if (checkPoints == null || checkPoints.size() == 0){
            return null;
        }
        return checkPoints.get(checkPoints.size() - 1);
    }
}
