package com.loserland.actors;

import greenfoot.Actor;

public interface IBallDecorator {
    BasicBall basicBall = null;
    void assemble(IBall basicBall);
    Actor getBall();
}
