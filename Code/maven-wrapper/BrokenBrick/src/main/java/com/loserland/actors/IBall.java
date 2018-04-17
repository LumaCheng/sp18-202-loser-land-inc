package com.loserland.actors;

import com.loserland.Utility.GifImage;

public interface IBall {
    void moveBall();
    void checkBallMiss();
    void makeSmoke();
    void brickCollision(Brick brick);
}
