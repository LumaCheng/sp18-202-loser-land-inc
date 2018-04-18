package com.loserland.actors;

public interface IBall {
    void moveBall();
    void checkBallMiss();
    void makeSmoke();
    void brickCollision(Brick brick);
}
