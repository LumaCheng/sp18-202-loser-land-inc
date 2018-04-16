package com.loserland.controller;

public interface ControllerSubject {

    void notifyObserver(ControllerEvent event);

    void addObserver(ControllerObserver observer);

    boolean removeObserver(ControllerObserver observer);

    void polling();
}
