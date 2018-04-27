package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.context.GameProgressManager;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import greenfoot.MouseInfo;
import greenfoot.World;

import java.util.ArrayList;
import java.util.List;


/**
 * Write a description of class MyWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */

public class MyWorld extends World
{
    // Declare variables, booleans and classes.
    private CoverPage menu;
    private GameOver gameOver;
    private MainWorld mainWorld;
    private PauseWorld pauseWorld;
    private MenuButton startGame;
    private MenuButton loadGame;
    private MenuButton highScore;
    private ICommand startClick ;
    private ICommand loadClick ;
    private ICommand scoreClick ;
    private HighScoreBoard highScoreBoard;
    private Back back;
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
    public MyWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(config.get(Integer.class, GameContext.WORLD_WIDTH), config.get(Integer.class, GameContext.WORLD_HEIGHT), config.get(Integer.class, GameContext.WORLD_CELL_SIZE));
        // Sets the order of display of Actors
        setPaintOrder(MenuButton.class, CoverPage.class, GameOver.class, Back.class, HighScoreBoard.class);
        //initialize UI components and put place
        initMenu();
    }




    private void initMenu() {
        //Creating three worlds
        mainWorld = new MainWorld();
        pauseWorld = new PauseWorld();
        mainWorld.setMyWorld(this);
        pauseWorld.setMyWorld(this);
        mainWorld.setPauseWorld(pauseWorld);
        pauseWorld.setMainWorld(mainWorld);

        //creating Buttons on the menu
        startGame = new MenuButton(config.get(GameContext.START_BUTTON), config.get(GameContext.START_HOVER),
                config.get(GameContext.START_PRESSED));
        loadGame = new MenuButton(config.get(GameContext.LOAD_BUTTON), config.get(GameContext.LOAD_HOVER),
                config.get(GameContext.LOAD_PRESSED));
        highScore = new MenuButton(config.get(GameContext.SCORE_BUTTON), config.get(GameContext.SCORE_HOVER),
                config.get(GameContext.SCORE_PRESSED));
        highScoreBoard = HighScoreBoard.getInstance();

        buttonsList.add(startGame);
        buttonsList.add(loadGame);
        buttonsList.add(highScore);

        startClick = new MenuCommand();
        loadClick = new MenuCommand();
        scoreClick = new MenuCommand();

        startClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        Greenfoot.setWorld(mainWorld);
                        startGame.resetImage();
                    }
                }
        ) ;

        loadClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        mainWorld.restore(GameProgressManager.getInstance().load());
                        Greenfoot.setWorld(mainWorld);
                        loadGame.resetImage();
                    }
                }
        );

        scoreClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        removeObject(gameOver);
                        removeObject(menu);
                        removeObject(startGame);
                        removeObject(highScore);
                        removeObject(loadGame);
                        highScore.resetImage();
                        highScoreBoard.ShowScore();
                    }
                }
        ) ;
        startGame.setCommand(startClick);
        loadGame.setCommand(loadClick);
        highScore.setCommand(scoreClick);

        //Add objects to MyWorld
        addObject (highScoreBoard, 350, 250);
        addObject (startGame, 615,395);
        addObject (loadGame, 615,435);
        addObject (highScore, 615,475);

        menu = new CoverPage();
        menu.setImage(config.get(GameContext.MENU_IMG));
        addObject (menu, 350,260);

        gameOver = new GameOver();
        addObject(gameOver, 350, 260);
        back = new Back();
        back.setImage(config.get(GameContext.BACK_BUTTON));
        addObject(back, 25, 25);
    }



    public void setGameOver() {
        removeObject(menu);
        removeObject(startGame);
        removeObject(loadGame);
        removeObject(highScore);
    }

    public void resetMainWorld() {
        mainWorld = new MainWorld();
        mainWorld.setMyWorld(this);
        mainWorld.setPauseWorld(pauseWorld);
        pauseWorld.setMainWorld(mainWorld);
    }

    // each act check for death, mouse input and whether to create new level
    public void act() {
        checkMouse();
        controller.polling();
    }




    public void checkMouse() {
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

        if(Greenfoot.mouseMoved(menu)){
            for(MenuButton menuButton: buttonsList){
                menuButton.resetImage();
            }
        }

        if (Greenfoot.mouseClicked(back)) {


                addObject (startGame, 615,395);
                addObject (loadGame, 615,435);
                addObject (highScore, 615,475);;
                addObject (gameOver, 350, 260);
                addObject (menu, 350, 260);


        }

        if (Greenfoot.mouseClicked(gameOver)){
                removeObject(gameOver);
                highScoreBoard.ShowScore();
        }
    }
}
