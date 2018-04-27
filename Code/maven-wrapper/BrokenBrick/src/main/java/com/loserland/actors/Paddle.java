package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.controller.ControllerEvent;
import com.loserland.controller.ControllerObserver;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;

/**
 * Write a description of class com.loserland.actors.Paddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Paddle extends Actor implements ControllerObserver {
    // Declare class
//    private BasicBall ball ;
    private boolean haveBall;
    private static boolean startBounce;
    private int enlarge ;
    private int shrink;        
    private int mouseX, mouseY;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    /**
     * Act - do whatever the com.loserland.actors.Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public Paddle() {
        super();
        setImage(config.get(GameContext.PADDLE_IMG));
    }
    // add new ball into world. Else NullPointerExeception
    public void addedToWorld(World world) 
    {
        newBall();
    } 

    public void act()
    {
        // sends information to expandImage method where it stretches the paddle
        if(enlarge > -1) {
            expandImage(getImage().getWidth()+1);
            // expand by 1 more animation
            enlarge++;
            // stop expansion when reached 25 times
            if(enlarge > 25) {
                enlarge = -1;
            }                   
        }
        // sends information to expandImage method where it stretches the paddle
        else if	(shrink > -1) {
            shrinkImage(getImage().getWidth()-1);
            // expand by 1 more animation
            shrink++;
            // stop expansion when reached 25 times
            if(shrink > 25) {
                shrink = -1;
            }            
        }
    }

    // method to expand image accordingly, frame by frame
    private void expandImage(int width) {   
        // initailize paddle image
        GreenfootImage img = getImage();
        // change its dimensions according to preference
        img.scale(width, img.getHeight());
        // refresh, display new paddle
        setImage(img);                            
    }

    private void shrinkImage(int width) {    
        if (width >=2)
        {        
            // initailize paddile image
            GreenfootImage img = getImage();
            // change its dimensions according to preference
            img.scale(width, img.getHeight());
            // refresh, display new paddle
            setImage(img);      
        }
    }

    // method called to create new ball after original ball dies and removed from world.
    public void newBall() 
    {
        BasicBall ball = new BasicBall();
        haveBall = true;
        startBounce = false;
        getWorld().addObject(ball, getX(), getY() - (ball.getImage().getHeight()));
    }

    // moves paddle accordingly with mouse input
    public void moveMe(int distance)
    {
        setLocation(getX()+distance, getY());

        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(BasicBall ball:ballList) {
            if (haveBall()) {
                // calls method in ball for ball to move along with paddle
                ball.move(distance);
            }
        }

    }
    // mutator to access boolean information of ball status
    public boolean haveBall()
    {
        return haveBall;
    }    
    // send value to Ball class to release ball
    public void releaseBall() {
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(BasicBall ball: ballList) {
            ball.launch(mouseX, mouseY);
        }
        // no more ball on paddle
        haveBall = false;
    }

    // method called from power up to start the expansion proccess.
    public void enlarge()
    {
        // 0<-1 thus, meets the if statement
        enlarge = 0;
    }

    // method called from power up to start the shrinking proccess.
    public void shrink()
    {
        // 0<-1 thus, meets the if statement
        shrink = 0;
    }

    @Override
    public boolean isInWorld() {
        return true;
    }

    public static void setStartBounce(boolean startBounce) {Paddle.startBounce = startBounce;}
    public static boolean getStartBounce() { return startBounce; }

    @Override
    public void controllerEventReceived(ControllerEvent event) {
        if (event.type == ControllerEvent.CommandType.MOVE) {
            mouseX = event.x;
            mouseY = event.y;
            if(haveBall == false) {
                if(mouseX > (getImage().getWidth()) / 3 &&
                   mouseX < (getWorld().getWidth() + 5) - getImage().getWidth() / 2) {
                    // calculate difference for actual magnitude moved
                    int changeX = mouseX - getX();
                    // move paddle accordingly
                    moveMe(changeX);
                }
            }
        }
    }

    public void setWidth(int width){
        expandImage(width);
    }
}
