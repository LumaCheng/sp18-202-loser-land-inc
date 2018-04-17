package com.loserland.controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Controller implements ControllerSubject {

    protected final List<ControllerObserver> observers;

    public Controller() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(ControllerObserver observer) {
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(ControllerObserver observer) {
        return observers.remove(observer);
    }

}
