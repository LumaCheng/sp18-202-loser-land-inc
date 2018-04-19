package com.loserland.utils;

import greenfoot.Actor;

public class Animation {
    public static void directionRotate(Actor actor, double changeX, double changeY) {
        double theta = Math.atan(changeY / changeX);
        double degrees = Math.toDegrees(theta);
        degrees += 90;

        if(changeX < 0)
            degrees += 180;

        if(actor.getRotation() != degrees)
            actor.setRotation((int) degrees);
    }

}
//int changeRotation = 0;
//setRotation(getRotation()+changeRotation);
//basicBall.changeRotation = Greenfoot.getRandomNumber(30);
//basicBall.setRotation(Greenfoot.getRandomNumber(360));