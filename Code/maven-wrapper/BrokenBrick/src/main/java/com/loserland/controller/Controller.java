package com.loserland.controller;

import greenfoot.World;
import java.util.ArrayList;
import java.util.List;


// TODO: Change to singleton pattern
public abstract class Controller implements ControllerSubject {

    protected World world;
    protected final List<ControllerObserver> observers;

    public Controller(World world) {
        this.world = world;
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(ControllerObserver observer) {
        observers.add(observer);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean removeObserver(ControllerObserver observer) {
        return observers.remove(observer);
    }
}
