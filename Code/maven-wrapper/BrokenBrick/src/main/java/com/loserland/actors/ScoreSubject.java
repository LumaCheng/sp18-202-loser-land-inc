package com.loserland.actors;

public interface ScoreSubject {
    void attach(ScoreObserver observer);
    void detach(ScoreObserver observer);
    void notifyObservers(int score);
}

