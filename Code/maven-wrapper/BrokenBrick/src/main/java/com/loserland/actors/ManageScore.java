package com.loserland.actors;

import java.util.ArrayList;
import java.util.List;


public class ManageScore implements ISubject {

    private List<IObserver> observers;

    public ManageScore(){
        observers = new ArrayList<>();
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int scores) {
        for (IObserver observer : observers){
                observer.update(scores);
        }
    }

}

