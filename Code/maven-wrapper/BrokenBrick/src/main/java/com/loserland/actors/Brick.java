package com.loserland.actors;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameBrick;
import com.loserland.context.GameContext;
import com.loserland.context.Storable;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import com.loserland.worlds.*;
public class Brick extends Actor implements Storable<GameBrick>
{
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    // declare and sets brick images to variables
    private  GreenfootImage brick1 = new GreenfootImage(config.get(GameContext.BRICK_LV1));
    private  GreenfootImage brick2 = new GreenfootImage(config.get(GameContext.BRICK_LV2));
    private  GreenfootImage brick3 = new GreenfootImage(config.get(GameContext.BRICK_LV3));
    private  GreenfootImage brick4 = new GreenfootImage(config.get(GameContext.BRICK_LV4));
    private  GreenfootImage brick5 = new GreenfootImage(config.get(GameContext.BRICK_LV5));
    private  GreenfootImage brick6 = new GreenfootImage(config.get(GameContext.BRICK_LV6));
    // hit variable made for differnt brick types. Bigger num = more hits required to kill
    private int hit = 0;
    // varibale for Brick6, where it takes 3 hits to destroy
    private int impact = 3;

    // Constructor, assign each brick with unique life and image.
    public Brick(int brickNum)
    {
        // brick 1 (easy)
        if (brickNum == 1)
        {
            setImage(brick1);
            hit = 1;
        }
        // brick 2 (easy)
        if (brickNum == 2)
        { 
            setImage(brick2);
            hit = 2;
        }
        // brick 3 (medium)
        if (brickNum == 3)
        {
            setImage(brick3);
            hit = 3;
        }     
        // brick 4 (medium)
        if (brickNum == 4)
        {
            setImage(brick4);
            hit = 4;
        }   
        // brick 5 (hard)
        if (brickNum == 5)
        {
            setImage(brick5);
            hit = 5;
        }   
        // brick 6 (hard)
        if (brickNum == 6)
        {
            setImage(brick6);
            hit = 6;
        }  
    }
    
    // called upon after brick collision with ball. Check brick color accordingly
    public void effect()
    {
        // rewards 5 points and remove brick
        if (hit == 1)
        {
            hit--;
            ((MainWorld)getWorld()).addPoints(5);
            getWorld().removeObject(this);
        }
        // rewards 8 points and change to brick 1
        if (hit==2)
        {
            hit--;
            ((MainWorld)getWorld()).addPoints(8);
            setImage(brick1);
        }
        // rewards 10 points and change to brick 2
        if (hit==3)
        {
            hit--;
            ((MainWorld)getWorld()).addPoints(10);
            setImage(brick2);
        }
        // rewards 14 points and change to brick 3
        if (hit==4)
        {
            hit--;
            ((MainWorld)getWorld()).addPoints(14);
            setImage(brick3);
        }
        // rewards 18 points and change to brick 4
        if (hit==5)
        {
            hit--;
            ((MainWorld)getWorld()).addPoints(18);
            setImage(brick4);
        }
        // rewards 5 points with each hit but rewards 40 after it dies after 3 hits
        if (hit==6)
        {
            // subtract 1 from life (total 3)
            impact--;          
            ((MainWorld)getWorld()).addPoints(5);
            // when destroyed reward 40 poitns
            if (impact == 0){
                ((MainWorld)getWorld()).addPoints(40);
                getWorld().removeObject(this);
            }
        }
    }

    public void killEffect() {
        while(getWorld() != null)
            effect();
    }
    // collision dectection for bricks
    public boolean checkHit()
    {
        Actor brick = getOneIntersectingObject(Brick.class);
        // return true if hits ball
        if ( brick != null )
        {
            return true;
        }
        // returns false if no collision
        else
        {
            return false;
        }
    }

    @Override
    public GameBrick save() {
        return new GameBrick(hit, getX(), getY());
    }
}
