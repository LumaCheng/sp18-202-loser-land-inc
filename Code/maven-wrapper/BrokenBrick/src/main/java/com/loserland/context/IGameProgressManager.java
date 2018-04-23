package com.loserland.context;

public interface IGameProgressManager {
    void add(GameCheckPoint checkPoint);
    GameCheckPoint load();
}