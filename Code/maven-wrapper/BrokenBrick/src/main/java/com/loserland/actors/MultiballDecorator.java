package com.loserland.actors;

import com.loserland.worlds.MainWorld;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class MultiballDecorator implements IBall, IBallDecorator {
    private BasicBall basicBall;
    int changeRotation = 0;

    @Override
    public void action() {
        if (!basicBall.onPaddle) {
            basicBall.moveBall();
            basicBall.checkBallMiss();
            basicBall.makeSmoke();
        }
        basicBall.setRotation(basicBall.getRotation()+changeRotation);
    }

    @Override
    public void moveBall() {
        basicBall.moveBall();
    }

    @Override
    public void checkBallMiss() {
        if (basicBall.getY() == basicBall.getWorld().getHeight()-1) {
            // send to method for update on counter
            ballDead();
            basicBall.getWorld().removeObject(basicBall);
        }
    }

    private void ballDead() {
        if(basicBall.getWorld().getObjects(BasicBall.class).size() == 1) {
            // reset to original position. Updates status to world
            ((MainWorld) basicBall.getWorld()).getStartAgain();
            ((MainWorld) basicBall.getWorld()).takeLife();
        }
    }

    @Override
    public void makeSmoke() {
        basicBall.makeSmoke();
    }

    @Override
    public void brickCollision(Brick brick) {
        basicBall.brickCollision(brick);
        changeRotation = Greenfoot.getRandomNumber(30);
        basicBall.setRotation(Greenfoot.getRandomNumber(360));
    }

    @Override
    public void wallCollision() {
        basicBall.wallCollision();
        changeRotation = Greenfoot.getRandomNumber(30);
        basicBall.setRotation(Greenfoot.getRandomNumber(360));
    }

    @Override
    public void bounce(Actor paddle) {
        basicBall.bounce(paddle);
        changeRotation = Greenfoot.getRandomNumber(30);
        basicBall.setRotation(Greenfoot.getRandomNumber(360));
    }

    @Override
    public PowerSquareFactory.PowerType getCurrentPower() {
        return PowerSquareFactory.PowerType.MULTI_BALL;
    }

    @Override
    public void assemble(IBall basicBall) {
        this.basicBall = (BasicBall)basicBall;
        this.basicBall.setDecorator(this);
        this.basicBall.setRotation(0);
    }

    @Override
    public Actor getBall() {
        return basicBall;
    }
}
