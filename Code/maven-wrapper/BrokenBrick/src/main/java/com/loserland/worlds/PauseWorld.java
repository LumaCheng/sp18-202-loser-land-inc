package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.*;
import greenfoot.MouseInfo;


/**
 * Write a description of class MyWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */

public class PauseWorld extends World
{
    // Declare variables, booleans and classes.
    private final int BRICKWIDTH = 45;
    private final int BRICKHEIGHT = 20;
    private final int VOFFSET = 12;
    private final int HOFFSET = 12;
    private MainWorld mainWorld;
    private MyWorld myWorld;
    private MenuOptions resume;
    private MenuOptions save;
    private MenuOptions exit;
    private PausePage pausePage;


    GreenfootSound backgroundMusic;

    // TODO: Using factory mode to initialize controller
    private Controller controller = new MouseController(this);

    //Configs
    private static ConfigFactory configFactory;
    public static Config config;

    static {
        configFactory = ConfigFactory.getInstance();
        config = configFactory.getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    }

    /**
     * Constructor for objects of class com.loserland.MainWorld.
     */
    public PauseWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(config.get(Integer.class, GameContext.WORLD_WIDTH), config.get(Integer.class, GameContext.WORLD_HEIGHT), config.get(Integer.class, GameContext.WORLD_CELL_SIZE));

        // Sets the order of display of Actors
        setPaintOrder(MenuOptions.class, PausePage.class);
        //initialize UI components and put place
        initMenu();

        initMusic();

        // clears screen instantly to show level 1
//        fader.fadeBackIn();
    }



    private void initMusic() {
        backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
        // play background music continuously

        //backgroundMusic.playLoop();
        //backgroundMusic.playLoop();

    }

    public void setMyWorld(MyWorld myWorld){
        this.myWorld = myWorld;
    }
    public void setMainWorld(MainWorld mainWorld) { this.mainWorld = mainWorld;}

    private void initMenu() {
        resume = new MenuOptions();
        resume.setImage(config.get(GameContext.RESUME_BUTTON));
        addObject (resume, 350,180);
        save = new MenuOptions();
        save.setImage(config.get(GameContext.SAVE_BUTTON));
        addObject (save, 350,250);
        exit = new MenuOptions();
        exit.setImage(config.get(GameContext.EXIT_BUTTON));
        addObject (exit, 350,320);
        pausePage = new PausePage();
        pausePage.setImage(config.get(GameContext.PAUSE_STAGE_IMG));
        addObject (pausePage, 350, 260);

        //initMusic();
        //gameOverSound();
        // Display GameOver screen
    }

    // each act check for death, mouse input and whether to create new level
    public void act() {
        checkMouse();
    }

    // checks if player looses life

    // checks for player input from mouse
    public void checkMouse() {
        // send cursor value to mouse variable
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int changeX;
        int mouseX;
        int mouseY;
        // check don't exceed left and right border of background
        // don't move paddle before player shoots
        //System.out.println(mouse.getActor());
        if (Greenfoot.mouseClicked(resume)) {
            Greenfoot.setWorld(mainWorld);
        }
        if (Greenfoot.mouseClicked(exit)) {
            Greenfoot.setWorld(myWorld);
            mainWorld.stopMusic();
            myWorld.resetMainWorld();
        }
        if(Greenfoot.mousePressed(resume)){
            resume.setImage(config.get(GameContext.RESUME_PRESSED));
        }
        if(Greenfoot.mouseMoved(resume)){
            resume.setImage(config.get(GameContext.RESUME_HOVER));
        }
        if(Greenfoot.mousePressed(save)){
            save.setImage(config.get(GameContext.SAVE_PRESSED));
        }
        if(Greenfoot.mouseMoved(save)){
            save.setImage(config.get(GameContext.SAVE_HOVER));
        }
        if(Greenfoot.mousePressed(exit)){
            exit.setImage(config.get(GameContext.EXIT_PRESSED));
        }
        if(Greenfoot.mouseMoved(exit)){
            exit.setImage(config.get(GameContext.EXIT_HOVER));
        }
        if(Greenfoot.mouseMoved(pausePage)){
            resume.setImage(config.get(GameContext.RESUME_BUTTON));
            save.setImage(config.get(GameContext.SAVE_BUTTON));
            exit.setImage(config.get(GameContext.EXIT_BUTTON));
        }
        if(Greenfoot.mouseClicked(save)){
            //save the current scene
        }

    }



}
