package com.loserland.actors;

import com.loserland.utils.GifImage;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import static java.lang.Math.*;
import com.loserland.worlds.*;

public class BasicBall extends SmoothMover implements IBall {
    private IBall ball = this;

    // declare variable, boolean and class
    double changeX;
    double changeY;
    double speed = 1;
    int smokeFrequency = 0;
    int smokeTimingCount = 0;
    double ballX = 0;
    double ballY = 0;
    private GifImage gifImage;

    // Theme settings
    private String ballHitWallSound = "";
    private String ballHitBrickSound = "";
    private String ballBounceSound = "";
    private double powerUpRate = 0.0;

    // set true since ball is on paddle at begining of level
    boolean onPaddle = true;

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public BasicBall() {
        setBallBounceSound("baseball.wav");
        setBallHitBrickSound("laser.wav");
        setBallHitWallSound("baseball.wav");
        setImage("ball.png");
        setBallInitCooridinate(350, 505);
        setSmokeFrequency(2);
        setPowerUpRate(0.3);
    }
    // each act, check for user input, make smoke and check death
    public void act()
    {
        ball.action();
    }

    public void action() {
        if(gifImage != null)
            setImage(gifImage.getCurrentImage());
        if (!onPaddle) {
            ball.moveBall();
            ball.checkBallMiss();
            ball.makeSmoke();
        }
    }
    private int calculateMovement(double movement, double speed) {
        int newMovement = (int)(movement * speed);
        if(movement > 0 && newMovement == 0) return 1;
        if(movement < 0 && newMovement == 0) return -1;
        return newMovement;
    }

    public void moveBall()
    {
        if(!onPaddle) {
            setLocation(changeX, changeY, speed);

            // Collision detection with paddle, brick and world edge
            checkPaddleCollision();
            checkWallCollision();
            checkBrickCollision();
        }
    }

    // Set location
    public void setLocation(double changeX, double changeY, double speed) {
        int newX = getX() + calculateMovement(changeX, speed);
        int newY = getY() + calculateMovement(changeY, speed);
        newX = (newX >= getWorld().getWidth())? getWorld().getWidth() - 1 : newX;
        newX = (newX < 0)? 0 : newX;
        newY = (newY >= getWorld().getHeight())? getWorld().getHeight() - 1 : newY;
        newY = (newY < 0)? 0 : newY;
        setLocation (newX, newY);
    }

    // check collision detection with wall
    public void checkWallCollision()
    {
        if (getX() <= 0 || getX() >= getWorld().getWidth() - 1) {
            changeX = -changeX;
            // sound effect
            if(ballHitWallSound != null)
                Greenfoot.playSound(ballHitWallSound);
        }

        // Makes ball move in opposite direction after collision
        if (getY() <= 0) {
            changeY = -changeY;
        }
    }

    // collision dectection with brick
    public void checkBrickCollision(){
        Brick brick = (Brick)getOneIntersectingObject(Brick.class);
        if ( brick != null ) {
            ball.brickCollision(brick);
            // sound effect
            if(ballHitBrickSound != null)
                Greenfoot.playSound(ballHitBrickSound);
        }
    }

    public void brickCollision(Brick brick){
        if (getY() > brick.getY() || getY() < brick.getY()) {
            changeY = -changeY;
            // Fixes multi-kill bug
            setLocation(getX(),getY() + 1);
            generatePowerSquare(brick);
        }
        else {
            // moves ball in opposite direction after collision
            changeX = -changeX;
        }
        // changes brick appearance accordingly
        brick.effect();

    }

    @Override
    public PowerSquareFactory.PowerType getCurrentPower() {
        return PowerSquareFactory.PowerType.NORMAL;
    }

    public void generatePowerSquare(Brick brick) {
        int hitNumber = Greenfoot.getRandomNumber((int)(PowerSquareFactory.getNumberOfPowers()/powerUpRate));
        if(hitNumber < PowerSquareFactory.getNumberOfPowers() - 1) {
            PowerSquareFactory.PowerType type = PowerSquareFactory.PowerType.values()[hitNumber];
            PowerSquare powerSquare;
            if(type == ball.getCurrentPower() ||
               (ball.getCurrentPower() != PowerSquareFactory.PowerType.NORMAL && hitNumber % 2 == 0))
                powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.NORMAL);
            else
                powerSquare = PowerSquareFactory.makePowerSquare(type);
            if(powerSquare != null) {
                getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
        }
    }

    // delete ball when passes MinX
    public void checkBallMiss()
    {
        if (getY() == getWorld().getHeight()-1) {
            // send to method for update on counter
            ballDead();
            getWorld().removeObject(this);
        }
    }

    private void ballDead()
    {
        // reset to original position. Updates status to world
        ((MainWorld)getWorld()).getStartAgain();
        ((MainWorld) getWorld()).takeLife();
    }

    // checks to see if ball made contact with paddle
    public void checkPaddleCollision()
    {
        Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
        if (paddle != null) {
            // bounce if made contact
            bounce(paddle);
        }
    }

    // ball collision with paddle
    private void bounce(Actor a) {
        // reflect opposite side
        changeY = -changeY;
        // refelcts depending on incoming angle
        int reflected = getX() - a.getX();
        // caculate angle of reflection based on incoming angle
        // divide by 8 to minimize the rebound magnitude. Not as dramatic/hard.
        changeX = changeX + (reflected/8);
        if (changeX > 7) {
            changeX = 7;
        }
        if (changeX < -7) {
            changeX = -7;
        }
        // sound effect
        if(ballBounceSound != null)
            Greenfoot.playSound(ballBounceSound);
    }

    public void resetBall() {
        Paddle paddle = getWorld().getObjects(Paddle.class).get(0);
        setLocation(paddle.getX(),paddle.getY()-(this.getImage().getHeight()+5));
    }

    public void replaceBall()
    {
        // sends information to world class (com.loserland.MainWorld) to call upon replaceBall method to create a new ball in place of old one.
        ((MainWorld) getWorld()).replaceBall();
    }

    /**
     * Method by Eddie Zhang
     */
    private void trajectoryPath(int mouseX, int mouseY)
    {
        // method created to calculate tragectory path.
        // calcuates distance of cursor from ball.
        double displacedX = abs(mouseX - ballX);
        double displacedY = abs(mouseY) - ballY;
        // Cosine Law to isolate for hypotenuse length
        double hypotenuse = abs(sqrt(( pow(displacedX,2)+ pow(displacedY,2))- (( 2*displacedX*displacedY)*(cos(90)))));
        /**finds angle of trajectory
         // 10 represents the length of travel for each act. personal preference
         // multiply by 180/PI to convert from radians to degrees
         // abs = absolute value
         // 1 degree = 180/PI radians (MUST convert to radians to use this method */
        double theta = abs(atan(displacedY/displacedX));
        // pythagorean theorem
        double actChangeX = abs((cos(theta))*8);
        // never negative so uses absolute value
        double actChangeY = abs((sin(theta))*8);
        // assign value accordinging to left or right aim
        changeY = -actChangeY;
        if (mouseX < ballX) {
            changeX = -actChangeX;
        }
        else {
            changeX = actChangeX;
        }
    }

    public void launch(int mouseX, int mouseY)
    {
        // change to negative so ball can move upwards
        trajectoryPath(mouseX,mouseY);
        // ball launched
        onPaddle = false;
    }

    // move ball along with paddle if not launched
    public void move(int distance)
    {
        setLocation(getX() + distance, getY());
    }

    // smoke effect, called from smoke class
    public void makeSmoke()
    {
        smokeTimingCount++;
        if(getWorld() != null) {
            if (smokeTimingCount % smokeFrequency == 0){
                getWorld().addObject(new Smoke(), getX(), getY());
            }
        }
    }

    public void setBallHitWallSound(String fileName) {
        ballHitWallSound = fileName;
    }

    public void setBallHitBrickSound(String fileName) {
        ballHitBrickSound = fileName;
    }

    public void setBallBounceSound(String fileName) {
        ballBounceSound = fileName;
    }
    public void setPowerUpRate(double rate) {
        powerUpRate = rate;
    }

    public void setSmokeFrequency(int frequency) {
        smokeFrequency = frequency;
    }

    public void setBallInitCooridinate(int x, int y) {
        ballX = x;
        ballY = y;
    }

    public void setDecorator(IBall ball) {
        if(ball == this) {
            setBallBounceSound("baseball.wav");
            setBallHitBrickSound("laser.wav");
            setBallHitWallSound("baseball.wav");
            setImage("ball.png");
            setSmokeFrequency(2);
        }
        this.ball = ball;
    }

    public void adjustSpeed(double speed) {
        double newSpeed = this.speed + speed;
        int newChangeX = (int) (changeX * newSpeed);
        int newChangeY = (int) (changeY * newSpeed);
        if (newChangeX != 0 && newChangeY != 0) {
            this.speed = newSpeed;
        }
    }

    public BasicBall clone() {
        return this;
    }

    public void setGifImage(String fileName, int delayMilliSec) {
        this.gifImage = new GifImage(fileName, delayMilliSec);
    }

    public void setImage(String fileName) {
        gifImage = null;
        super.setImage(fileName);
    }
}
