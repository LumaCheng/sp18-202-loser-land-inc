package com.loserland.context;

public interface IGameProgress {

    GameCheckPoint save();
    void restore(GameCheckPoint checkPoint);

}
