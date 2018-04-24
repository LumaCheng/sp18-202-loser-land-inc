package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.utils.GifImage;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.util.List;

public class PyroblastDecorator implements IBall, IBallDecorator{
    private BasicBall basicBall;
    private GreenfootImage explodeImage;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public PyroblastDecorator() {
        explodeImage = new GifImage(config.get(GameContext.EXPLOSION_IMG)).getImages().get(0);
    }

    @Override
    public void action() {
        if(basicBall.gifImage != null)
            basicBall.setImage(basicBall.gifImage.getCurrentImage());
        if (!basicBall.onPaddle) {
            moveBall();
            checkBallMiss();
            makeSmoke();
        }
    }

    @Override
    public void moveBall() {
        basicBall.moveBall();
    }

    @Override
    public void checkBallMiss() {
        basicBall.checkBallMiss();
    }

    @Override
    public void makeSmoke() {
        basicBall.smokeTimingCount++;
        if(basicBall.getWorld() != null) {
            if (basicBall.smokeTimingCount % basicBall.smokeFrequency == 0){
                Smoke smoke = new Smoke();
                smoke.setImage(config.get(GameContext.PYTOBLAST_SMOKE));
                basicBall.getWorld().addObject(smoke, basicBall.getX(), basicBall.getY());
            }
        }
    }

    @Override
    public void brickCollision(Brick brick) {
        int h = explodeImage.getHeight();
        int w = explodeImage.getWidth();
        int radius = h > w ? h / 2 : w / 2;
        List<Brick> brickList = basicBall.getObjectsInRange(radius, Brick.class);

        for(Brick b:brickList) {
            b.killEffect();
        }

        if(brickList.size() > 0) {
            Explosion explosion = new Explosion();
            basicBall.getWorld().addObject(explosion, basicBall.getX(), basicBall.getY());
            basicBall.setDecorator(basicBall);
        }
    }

    @Override
    public void wallCollision() {
        basicBall.wallCollision();
    }

    @Override
    public void bounce(Actor actor) {
        basicBall.bounce(actor);
    }

    @Override
    public PowerSquareFactory.PowerType getCurrentPower() {
        return PowerSquareFactory.PowerType.PYROBLAST_BALL;
    }

    @Override
    public void assemble(IBall basicBall) {
        this.basicBall = (BasicBall)basicBall;
        this.basicBall.setDecorator(this);
        this.basicBall.setImage(config.get(GameContext.PYTOBLAST_BALL));
    }

    @Override
    public Actor getBall() {
        return basicBall;
    }
}
