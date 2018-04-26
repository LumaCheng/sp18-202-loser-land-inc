package com.loserland.actors;

import java.util.ArrayList;
import java.util.List;


public class ManageScore implements ScoreSubject {

    List<ScoreObserver> observers;


    public ManageScore(){
        observers = new ArrayList<>();
    }

    @Override
    public void attach(ScoreObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(ScoreObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int scores) {
        for (ScoreObserver observer : observers){

                observer.update(scores);



        }
    }

}

