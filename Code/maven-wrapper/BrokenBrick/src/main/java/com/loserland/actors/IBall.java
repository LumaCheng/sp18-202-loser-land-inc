package com.loserland.actors;

public interface IBall {
    void action();
    void moveBall();
    void checkBallMiss();
    void makeSmoke();
    void brickCollision(Brick brick);
    PowerSquareFactory.PowerType getCurrentPower();
}
