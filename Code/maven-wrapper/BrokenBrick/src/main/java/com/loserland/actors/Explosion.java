package com.loserland.actors;

import com.loserland.utils.GifImage;
import greenfoot.Actor;

public class Explosion extends Actor {
    GifImage gifImage;

    public Explosion() {
        gifImage = new GifImage("explosion.gif", 30);
    }

    @Override
    public void act() {
//        setImage(gifImage.getCurrentImage());
        if(gifImage.isRepeated()) {
            getWorld().removeObject(this);
        } else {
            setImage(gifImage.getCurrentImage());
        }
    }
}
