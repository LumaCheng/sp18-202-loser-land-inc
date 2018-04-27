package com.loserland.controller;

import greenfoot.Actor;

public interface ControllerSubject {

    void notifyObserver(ControllerEvent event);

    void addObserver(ControllerObserver observer);

    boolean removeObserver(ControllerObserver observer);

    boolean clicked(Actor actor);

    void polling();
}
