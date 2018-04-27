
package com.loserland.actors;
import com.loserland.context.GameLevel;
import com.loserland.context.Storable;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
// allow to change color

// allow to change font

public class LevelLabel extends Actor implements Storable<GameLevel>
{  
    // Declare instance constants. Value set and cannot be changed
    // width of the score board
    private final int WIDTH = 85;  
    // height of the score board
    private final int HEIGHT = 20;      

    // The constructor composes the image for the com.loserland.actors.ScoreBoard.
//    public LevelLabel()
//    {
//        // initalized new image
//        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);
//        // cosmetic porcedures for color and font
//        Font myFont = new Font("TIMES", true, false, 12);
//        image.setFont(myFont);
//        // display on screen
//        setImage(image);
//        // Set level to 1 at begining
//        setGameLevel(1);
//    }
    private int level;

    public LevelLabel(int level)
    {
        this.level = level;
        // initalized new image
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);
        // cosmetic porcedures for color and font
        Font myFont = new Font("TIMES", true, false, 12);
        image.setFont(myFont);
        // display on screen
        setImage(image);
        // Set level to 1 at begining
        setLevel(level);
    }

    // updates the score display
    public void setLevel(int level)
    {
        this.level = level;
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
        image.drawString("Level: " + level, x, y);
        setImage(image);
    }

    @Override
    public GameLevel save() {
        return new GameLevel(level, getX(), getY());
    }
}