package com.loserland.actors;
import greenfoot.*;
import com.loserland.worlds.*;

public class FireBallDecorator implements IBall, IBallDecorator {
    private BasicBall basicBall;

    public void assemble(IBall basicBall) {
        this.basicBall = (BasicBall)basicBall;
        this.basicBall.setDecorator(this);
        this.basicBall.setShouldRotate(false);
        this.basicBall.setGifImage("fireball.gif", 30);
    }

    public void moveBall() {
        basicBall.moveBall();
    }


    public void checkBallMiss() {
        basicBall.checkBallMiss();
    }


    public void makeSmoke() {
        basicBall.makeSmoke();
    }

    @Override
    public void brickCollision(Brick brick) {
        if(basicBall.shouldRotate) {
            basicBall.changeRotation = Greenfoot.getRandomNumber(30);
            basicBall.setRotation(Greenfoot.getRandomNumber(360));
        }
        basicBall.generatePowerSquare(brick);
        brick.effect();
    }

    public Actor getBall() {
        return basicBall;
    }



}
