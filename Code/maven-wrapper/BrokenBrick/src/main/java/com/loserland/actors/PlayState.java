package com.loserland.actors;
import greenfoot.*;
import greenfoot.GreenfootImage;

public class PlayState extends Actor implements State  {


    public void PlayState(){


    }

    public void act() {


    }

    public GreenfootImage getImage(){
        return new GreenfootImage("pause.png");
    }

    public void doAction(Musicplayer musicplayer){
        musicplayer.setState(this);
    }
}
