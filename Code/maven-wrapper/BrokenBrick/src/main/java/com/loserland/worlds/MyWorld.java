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


    private boolean ifMainMenu = true;
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

        //initMusic();

        // clears screen instantly to show level 1
//        fader.fadeBackIn();
    }



    private void initMusic() {
        backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
        // play background music continuously

        //backgroundMusic.playLoop();
        //backgroundMusic.playLoop();

    }

    public void setGameOver() {
        removeObject(menu);
        removeObject(startGame);
        removeObject(loadGame);
        removeObject(highScore);
        ifMainMenu = false;
    }

    public void resetMainWorld() {
        mainWorld = new MainWorld();
        mainWorld.setMyWorld(this);
        mainWorld.setPauseWorld(pauseWorld);
        pauseWorld.setMainWorld(mainWorld);
    }




    private void initMenu() {
        mainWorld = new MainWorld();
        pauseWorld = new PauseWorld();
        mainWorld.setMyWorld(this);
        pauseWorld.setMyWorld(this);
        mainWorld.setPauseWorld(pauseWorld);
        pauseWorld.setMainWorld(mainWorld);


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




        addObject (highScoreBoard, 350, 260);
        addObject (startGame, 350,360);
        addObject (loadGame, 350,410);
        addObject (highScore, 350,460);

        menu = new CoverPage();
        menu.setImage(config.get(GameContext.MENU_IMG));
        addObject (menu, 350,260);

        initMusic();
        //gameOverSound();
        // Display GameOver screen
        gameOver = new GameOver();
        addObject(gameOver, 350, 260);
        back = new Back();
        back.setImage(config.get(GameContext.BACK_BUTTON));
        addObject(back, 25, 25);
    }

    // each act check for death, mouse input and whether to create new level
    public void act() {
        checkMouse();
        controller.polling();
    }

    // checks if player looses life

    // return boolean after gameover sound played
    public void gameOverSound() {
        Greenfoot.playSound("gameOver.wav");
    }


    // checks for player input from mouse
    public void checkMouse() {
        // send cursor value to mouse variable
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int changeX;
        int mouseX;
        int mouseY;

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


        // check don't exceed left and right border of background
        // don't move paddle before player shoots
        //System.out.println(mouse.getActor());
//        if (Greenfoot.mouseClicked(startGame)) {
//            if (ifMainMenu) {
//                // once clicked, remove menu
//                Greenfoot.setWorld(mainWorld);
//                startGame.setImage(config.get(GameContext.START_BUTTON));
//                // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.
//            }
//        }

//        if (Greenfoot.mouseClicked(highScore)) {
//            if (ifMainMenu) {
//                // once clicked, remove menu
//                removeObject(gameOver);
//                removeObject(menu);
//                removeObject(startGame);
//                removeObject(highScore);
//                removeObject(loadGame);
//                ifMainMenu = false;
//                highScore.setImage(config.get(GameContext.SCORE_BUTTON));
//                highScoreBoard.ShowScore();
//                // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.
//            }
//        }

        if (Greenfoot.mouseClicked(back)) {

                // once clicked, remove menu
                addObject (startGame, 350,360);
                addObject (loadGame, 350,410);
                addObject (highScore, 350,460);
                addObject (gameOver, 350, 260);
                addObject (menu, 350, 260);
                // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.

        }

//        if(Greenfoot.mousePressed(startGame)){
//            startGame.setImage(config.get(GameContext.START_PRESSED));
//        }
//        if(Greenfoot.mouseMoved(startGame)){
//            startGame.setImage(config.get(GameContext.START_HOVER));
//            loadGame.setImage(config.get(GameContext.LOAD_BUTTON));
//            highScore.setImage(config.get(GameContext.SCORE_BUTTON));
//        }
//        if(Greenfoot.mousePressed(loadGame)){
//            loadGame.setImage(config.get(GameContext.LOAD_PRESSED));
//        }



//        if(Greenfoot.mouseClicked(loadGame)){
//            // Load Game Scene
//            mainWorld.restore(GameProgressManager.getInstance().load());
//            Greenfoot.setWorld(mainWorld);
//        }


//        if(Greenfoot.mouseMoved(loadGame)){
//            loadGame.setImage(config.get(GameContext.LOAD_HOVER));
//            startGame.setImage(config.get(GameContext.START_BUTTON));
//            highScore.setImage(config.get(GameContext.SCORE_BUTTON));
//        }
//        if(Greenfoot.mousePressed(highScore)){
//            highScore.setImage(config.get(GameContext.SCORE_PRESSED));
//        }
//        if(Greenfoot.mouseMoved(highScore)){
//            highScore.setImage(config.get(GameContext.SCORE_HOVER));
//            loadGame.setImage(config.get(GameContext.LOAD_BUTTON));
//            startGame.setImage(config.get(GameContext.START_BUTTON));
//        }
//        if(Greenfoot.mouseMoved(menu)){
//            startGame.setImage(config.get(GameContext.START_BUTTON));
//            loadGame.setImage(config.get(GameContext.LOAD_BUTTON));
//            highScore.setImage(config.get(GameContext.SCORE_BUTTON));
//        }

        if (Greenfoot.mouseClicked(gameOver)){
            //mainWorld.stopMusic();
                removeObject(gameOver);
                highScoreBoard.ShowScore();
                //refreshMainWorld();
        }
    }
}
