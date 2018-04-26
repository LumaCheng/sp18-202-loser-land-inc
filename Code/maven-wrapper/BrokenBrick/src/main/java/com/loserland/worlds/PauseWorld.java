package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameCheckPoint;
import com.loserland.context.GameContext;
import com.loserland.context.GameProgressManager;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.*;
import greenfoot.MouseInfo;

import java.util.ArrayList;
import java.util.List;


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
    private MenuButton resume;
    private MenuButton save;
    private MenuButton exit;
    private ICommand resumeClick ;
    private ICommand saveClick ;
    private ICommand exitClick ;
    private PausePage pausePage;
    private List<MenuButton> buttonsList = new ArrayList<>();

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
        setPaintOrder(MenuButton.class, PausePage.class);
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
        resume = new MenuButton(config.get(GameContext.RESUME_BUTTON), config.get(GameContext.RESUME_HOVER),
                config.get(GameContext.RESUME_PRESSED));
        save = new MenuButton(config.get(GameContext.SAVE_BUTTON), config.get(GameContext.SAVE_HOVER),
                config.get(GameContext.SAVE_PRESSED));
        exit = new MenuButton(config.get(GameContext.EXIT_BUTTON), config.get(GameContext.EXIT_HOVER),
                config.get(GameContext.EXIT_PRESSED));
        buttonsList.add(resume);
        buttonsList.add(save);
        buttonsList.add(exit);

        resumeClick = new MenuCommand();
        saveClick = new MenuCommand();
        exitClick = new MenuCommand();
        resumeClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        Greenfoot.setWorld(mainWorld);
                        resume.resetImage();
                    }
                }
        ) ;

        saveClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        System.out.println("save clicked");
                        GameProgressManager.getInstance().add(new GameCheckPoint(mainWorld.getCurrentState()));
                        save.resetImage();
                    }
                }
        );

        exitClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        Greenfoot.setWorld(myWorld);
                        mainWorld.stopMusic();
                        myWorld.resetMainWorld();
                        exit.resetImage();
                    }
                }
        ) ;
        resume.setCommand(resumeClick);
        save.setCommand(saveClick);
        exit.setCommand(exitClick);


        addObject (resume, 350,180);
        addObject (save, 350,250);
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
        for(MenuButton menuButton: buttonsList){
            if(Greenfoot.mouseClicked(menuButton)){
                menuButton.click();
            }
            if(Greenfoot.mouseMoved(menuButton)){
                menuButton.hover();
                for(MenuButton button: buttonsList){
                    if(button != menuButton){
                        button.resetImage();
                    }
                }
            }
            if(Greenfoot.mousePressed(menuButton)){
                menuButton.press();
            }
        }

        if(Greenfoot.mouseMoved(pausePage)){
        
            for(MenuButton menuButton: buttonsList){
                menuButton.resetImage();
            }
        }
    }
}
