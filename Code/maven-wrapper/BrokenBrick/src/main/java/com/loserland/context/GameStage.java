package com.loserland.context;

import greenfoot.Actor;

import java.util.ArrayList;
import java.util.List;

public class GameStage {
    private List<Actor> actors;



    public GameStage() {
        actors = new ArrayList<>();
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
    }

    public enum Stage {
        DEFAULT,
    }
}
