package com.loserland.actors;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.loserland.context.BallPool;
import com.loserland.worlds.MainWorld;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class MultiballDecorator implements IBall, IBallDecorator {
    private BasicBall basicBall;
    int changeRotation = 0;

    @Override
    public void action() {
        if (!basicBall.onPaddle) {
            moveBall();
            checkBallMiss();
            makeSmoke();
        }
        basicBall.setRotation(basicBall.getRotation()+changeRotation);
    }

    @Override
    public void moveBall()
    {
        if(!basicBall.onPaddle) {
            basicBall.setLocation(basicBall.changeX, basicBall.changeY, basicBall.speed);

            // Collision detection with paddle, brick and world edge
            basicBall.checkPaddleCollision();
            basicBall.checkWallCollision();
            basicBall.checkBrickCollision();
//            checkBallCollision();
        }
    }

//    private void checkBallCollision() {
//        BasicBall ball = (BasicBall)basicBall.getOneIntersectingObject(BasicBall.class);
//        if ( ball != null ) {
//            if (basicBall.getY() > ball.getY() || basicBall.getY() < ball.getY()) {
//                basicBall.changeY = -basicBall.changeY;
//                // Fixes multi-kill bug
//                basicBall.setLocation(basicBall.getX() + basicBall.changeX + Greenfoot.getRandomNumber(10)-5,
//                        basicBall.getY() + basicBall.changeY + Greenfoot.getRandomNumber(10)-5);
//            }
//            else{
//                // moves ball in opposite direction after collision
//                basicBall.changeX = -basicBall.changeX;
//            }
//            ball.speed+=0.001;
//            basicBall.speed+=0.001;
//        }
//    }

    @Override
    public void checkBallMiss() {
        basicBall.checkBallMiss();
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
        // sound effect
        if(basicBall.ballHitBrickSound != null)
            Greenfoot.playSound(basicBall.ballHitBrickSound);
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
    public PowerSquareFactory.PowerType powerType() {
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
