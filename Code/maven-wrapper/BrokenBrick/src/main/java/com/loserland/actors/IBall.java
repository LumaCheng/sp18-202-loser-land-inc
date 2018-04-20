package com.loserland.actors;

import greenfoot.Actor;

public interface IBall {
    void action();
    void moveBall();
    void checkBallMiss();
    void makeSmoke();
    void brickCollision(Brick brick);
    void wallCollision();
    void bounce(Actor actor);
    PowerSquareFactory.PowerType getCurrentPower();
}
