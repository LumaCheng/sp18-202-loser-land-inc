package com.loserland.worlds;
import com.loserland.actors.CoverPage;
import com.loserland.actors.Fader;
import com.loserland.actors.GameOver;
import com.loserland.actors.MenuOptions;
import com.loserland.actors.Paddle;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import greenfoot.MouseInfo;
import greenfoot.World;


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
    private MenuOptions startGame;
    private MenuOptions loadGame;
    private MenuOptions highScore;
    private boolean ifMainMenu = true;

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
        loadGame = new MenuOptions();
        loadGame.setImage(config.get(GameContext.LOAD_BUTTON));
        highScore = new MenuOptions();
        highScore.setImage(config.get(GameContext.SCORE_BUTTON));
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
        if(Greenfoot.mousePressed(loadGame)){
            loadGame.setImage(config.get(GameContext.LOAD_PRESSED));
        }
        if(Greenfoot.mouseMoved(loadGame)){
            loadGame.setImage(config.get(GameContext.LOAD_HOVER));
        }
        if(Greenfoot.mousePressed(highScore)){
            highScore.setImage(config.get(GameContext.SCORE_PRESSED));
        }
        if(Greenfoot.mouseMoved(highScore)){
            highScore.setImage(config.get(GameContext.SCORE_HOVER));
        }
        if(Greenfoot.mouseMoved(menu)){
            startGame.setImage(config.get(GameContext.START_BUTTON));
            loadGame.setImage(config.get(GameContext.LOAD_BUTTON));
            highScore.setImage(config.get(GameContext.SCORE_BUTTON));
        }

        if (Greenfoot.mouseClicked(gameOver)) {
            mainWorld.stopMusic();
            if (!ifMainMenu) {
                addObject(menu, 350, 260);
                addObject(startGame, 350, 360);
                ifMainMenu = true;
                //refreshMainWorld();
            }
        }
    }


}
