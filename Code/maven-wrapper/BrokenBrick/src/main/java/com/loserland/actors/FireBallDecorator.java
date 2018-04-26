package com.loserland.actors;
import greenfoot.*;

import static com.loserland.utils.Animation.directionRotate;
import com.loserland.context.GameContext;
import com.loserland.configs.*;

public class FireBallDecorator implements IBall, IBallDecorator {
    private BasicBall basicBall;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public void action() {
        if(basicBall.gifImage != null)
            basicBall.setImage(basicBall.gifImage.getCurrentImage());
        if (!basicBall.onPaddle) {
            moveBall();
            checkBallMiss();
            makeSmoke();
            rotate();
        }
    }
    public void assemble(IBall basicBall) {
        this.basicBall = (BasicBall)basicBall;
        this.basicBall.setDecorator(this);
        this.basicBall.setRotation(0);
        this.basicBall.setGifImage(config.get(GameContext.FIREBALL), 60);
    }

    public void moveBall() {
        if(!basicBall.onPaddle) {
            basicBall.setLocation(basicBall.changeX, basicBall.changeY, basicBall.speed);

            // Collision detection with paddle, brick and world edge
            basicBall.checkPaddleCollision();
            basicBall.checkWallCollision();
            basicBall.checkBrickCollision();
        }
    }

    public void brickCollision(Brick brick){
        PowerGenerator.generatePowerSquare(basicBall, brick);
        brick.effect();
    }

    @Override
    public void wallCollision() {
        basicBall.wallCollision();
    }

    @Override
    public void bounce(Actor paddle) {
        basicBall.bounce(paddle);
    }


    public void checkBallMiss() {
        basicBall.checkBallMiss();
    }


    @Override
    public PowerSquareFactory.PowerType getCurrentPower() {
        return PowerSquareFactory.PowerType.FIRE_BALL;
    }

    public void makeSmoke()
    {
        basicBall.smokeTimingCount++;
        if(basicBall.getWorld() != null) {
            if (basicBall.smokeTimingCount % basicBall.smokeFrequency == 0){
                Smoke smoke = new Smoke();
                smoke.setImage(config.get(GameContext.FIRE_SMOKE));
                basicBall.getWorld().addObject(smoke, basicBall.getX(), basicBall.getY());
            }
        }
    }

    public void rotate() {
        directionRotate(basicBall, basicBall.changeX, basicBall.changeY);
    }

    public Actor getBall() {
        return basicBall;
    }

}
