package com.loserland.actors;

import greenfoot.GreenfootImage;

public interface State {
     void doAction(Musicplayer musicplayer);
     GreenfootImage getImage();
}
