package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.context.GameScore;
import com.loserland.context.Storable;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
// allow to change color

// allow to change font

import java.awt.Graphics;
public class ScoreBoard extends Actor implements IObserver, Storable<GameScore>
{
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    // declare instance variables
    // width of the score board
    private int WIDTH = Integer.parseInt(config.get(GameContext.SCOREBOARD_WIDTH));
    // height of the score board
    private int HEIGHT = Integer.parseInt(config.get(GameContext.SCOREBOARD_HEIGHT));
    private int score;

    // The constructor composes the image for the com.loserland.actors.ScoreBoard.

    public ScoreBoard(int score)
    {
        // initalized new image
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);
        // cosmetic porcedures for color and font
        Font myFont = new Font("TIMES", true, false, 12);
        image.setFont(myFont);
        // display on screen
        setImage(image);
        // Set score to 0 at begining
        update(score);
    }

    // updates the score display
    @Override
    public void update(int s)
    {
        score = s;
        // x and y relative to the image. baseline of leftmost character.
        int x = 5;      
        int y = 15;        
        // "Repaint" the score display         
        GreenfootImage image = getImage();
        // background color
        image.setColor(Color.DARK_GRAY);
        // "erase" the display
        image.fillRect(1, 1, WIDTH-2, HEIGHT-2);  
        // text color
        image.setColor(Color.WHITE);      

        // display image onto screen in place of the previous
        image.drawString("Score: " + score, x, y);  
        setImage(image);         
    }

    @Override
    public GameScore save() {
        return new GameScore(score, getX(), getY());
    }
}
