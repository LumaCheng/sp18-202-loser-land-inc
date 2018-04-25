package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.context.*;
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

public class MyWorld extends World
{
    // Declare variables, booleans and classes.
    private final int BRICKWIDTH = 45;
    private final int BRICKHEIGHT = 20;
    private final int VOFFSET = 12;
    private final int HOFFSET = 12;
    private Paddle paddle;
    private Fader fader;
    private CoverPage menu;
    private GameOver gameOver;
    private MainWorld mainWorld;
    private PauseWorld pauseWorld;
    private MenuOptions startGame;
    private boolean ifMainMenu = true;

    GreenfootSound backgroundMusic;

    // TODO: Using factory mode to initialize controller
    private Controller controller = new MouseController();

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
        setPaintOrder(MenuOptions.class, CoverPage.class, GameOver.class);

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
        startGame = new MenuOptions();
        startGame.setImage(config.get(GameContext.START_BUTTON));
        addObject (startGame, 350,360);
        menu = new CoverPage();
        menu.setImage(config.get(GameContext.MENU_IMG));
        addObject (menu, 350,260);
        initMusic();
        //gameOverSound();
        // Display GameOver screen
        gameOver = new GameOver();
        addObject(gameOver, 350, 260);
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
        // check don't exceed left and right border of background
        // don't move paddle before player shoots
        //System.out.println(mouse.getActor());
        if (Greenfoot.mouseClicked(startGame)) {
            if (ifMainMenu) {
                // once clicked, remove menu
                Greenfoot.setWorld(mainWorld);
                startGame.setImage(config.get(GameContext.START_BUTTON));
                // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.
            }
        }
        if(Greenfoot.mousePressed(startGame)){
            startGame.setImage(config.get(GameContext.START_PRESSED));
        }
        if(Greenfoot.mouseMoved(startGame)){
            startGame.setImage(config.get(GameContext.START_HOVER));
        }
        if(Greenfoot.mouseMoved(menu)){
            startGame.setImage(config.get(GameContext.START_BUTTON));
        }

        if (Greenfoot.mouseClicked(gameOver)) {
            mainWorld.stopMusic();
            resetMainWorld();
            if (!ifMainMenu) {
                addObject(menu, 350, 260);
                addObject(startGame, 350, 360);
                ifMainMenu = true;
                //refreshMainWorld();
            }
        }
    }

    public void replaceBall()
    {
        // call method from paddle to create new ball Actor into world
        paddle.newBall();
    }

}
