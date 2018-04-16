package com.loserland.controller;

public interface ControllerObserver {

    boolean isInWorld();

    void controllerEventReceived(ControllerEvent event);

}
