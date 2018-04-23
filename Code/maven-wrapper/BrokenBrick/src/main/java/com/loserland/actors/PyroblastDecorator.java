package com.loserland.actors;

import com.loserland.utils.GifImage;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.List;

public class PyroblastDecorator implements IBall, IBallDecorator{
    private BasicBall basicaBall;
    private GreenfootImage explodeImage;

    public PyroblastDecorator() {
        explodeImage = new GifImage("explosion.gif").getImages().get(0);
    }

    @Override
    public void action() {
        basicaBall.action();
    }

    @Override
    public void moveBall() {
        basicaBall.moveBall();
    }

    @Override
    public void checkBallMiss() {
        basicaBall.checkBallMiss();
    }

    @Override
    public void makeSmoke() {
        basicaBall.makeSmoke();
    }

    @Override
    public void brickCollision(Brick brick) {
        int h = explodeImage.getHeight();
        int w = explodeImage.getWidth();
        int radius = h > w ? h : w;
        List<Brick> brickList = basicaBall.getObjectsInRange(radius, Brick.class);

        for(Brick b:brickList) {
            b.killEffect();
        }

        if(brickList.size() > 0) {
            Explosion explosion = new Explosion();
            basicaBall.getWorld().addObject(explosion, basicaBall.getX(), basicaBall.getY());
            basicaBall.setDecorator(basicaBall);
        }
    }

    @Override
    public void wallCollision() {
        basicaBall.wallCollision();
    }

    @Override
    public void bounce(Actor actor) {
        basicaBall.bounce(actor);
    }

    @Override
    public PowerSquareFactory.PowerType getCurrentPower() {
        return PowerSquareFactory.PowerType.PYROBLAST_BALL;
    }

    @Override
    public void assemble(IBall basicBall) {
        this.basicaBall = (BasicBall)basicBall;
        this.basicaBall.setDecorator(this);
        this.basicaBall.setImage("pyroblast.png");
    }

    @Override
    public Actor getBall() {
        return basicaBall;
    }
}
