package com.loserland.actors;
import greenfoot.*;
import greenfoot.GreenfootImage;


public class PauseState extends Actor implements State {


    public void PauseState(){

    }

    public void act() {


    }
    public GreenfootImage getImage(){

        return new GreenfootImage("play.png");
    }

    public void doAction(Musicplayer musicplayer) {
        musicplayer.setState(this);
    }
}