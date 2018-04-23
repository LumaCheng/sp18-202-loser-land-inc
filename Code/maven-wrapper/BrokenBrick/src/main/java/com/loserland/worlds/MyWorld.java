package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.*;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.*;
import greenfoot.MouseInfo;

import java.awt.*;
import java.util.List;


/**
 * Write a description of class MyWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */
public class MyWorld extends World implements IGameProgressManager
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
    private ScoreBoard scoreBoard=new ScoreBoard();

    private Counter levelNum = new Counter();
    private Pointy aim = new Pointy();  

    private Musicplayer musicplayer = new Musicplayer();
    private Volumeup volumeup = new Volumeup();
    private Volumedown volumedown = new Volumedown();

    // a total of 4 lives per game
    private int lives = 4;
    // start score from 0
    private int score = 0;
    // start game with level 1
    private int level = 1;
    // create 3 new life "bars"
    private Lives live1 = new Lives();
    private Lives live2 = new Lives();
    private Lives live3 = new Lives();
    // initalize background music, save was "backgroundMusic"
    GreenfootSound backgroundMusic;
    // boolean to determine if ball was launched
    private boolean start = false;
    // boolean to determine is gameOver music was played
    private boolean played = false;
    // instead of boolean uses integers to meet if statement for main menu. Fixes launch bug where ball launches immediatly after menu.
    private int clickMenu = 1;
    //volume
    private int volume = 65;

    // TODO: Using factory mode to initialize controller
    private Controller controller = new MouseController();

    //Configs
    private static ConfigFactory configFactory;
    public static Config config;

    static {
        configFactory = ConfigFactory.getInstance();
        config = configFactory.getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    }

    private GameState currentState;
    private GameStageLoader stageLoader;

    /**
     * Constructor for objects of class com.loserland.MyWorld.
     *
     */
    public MyWorld()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(config.get(Integer.class, GameContext.WORLD_WIDTH), config.get(Integer.class, GameContext.WORLD_HEIGHT), config.get(Integer.class, GameContext.WORLD_CELL_SIZE));

        // Sets the order of display of Actors
        setPaintOrder(CoverPage.class,GameOver.class, Fader.class,BasicBall.class,Pointy.class,Paddle.class, Smoke.class, Lives.class, ScoreBoard.class, Counter.class);

        //init game state
        currentState = new GameState();
        currentState.setStage(GameStageLoader.getInstance().load());

        //initialize UI components and put place
        render(currentState);

        initUI();
        initMusic();

        // clears screen instantly to show level 1
        fader.fadeBackIn();

        controller.addObserver(aim);

    }

    private void render(GameState state) {

        //render stage
        for (GameBrick gameBrick: state.getStage().getBricks()){
            Brick brick = new Brick(gameBrick.getType());
            addObject(brick, gameBrick.getX(), gameBrick.getY());
            if (hasIntersectingActors(brick, Brick.class)){
                removeObject(brick);
            }
        }
    }

    private void initMusic() {
        backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
        // play background music continuously

        //backgroundMusic.playLoop();
        backgroundMusic.playLoop();

    }

    private void initUI() {
        // create new paddle and ball
        paddle = new Paddle();

        // add paddle into world
        addObject(paddle, getWidth()/2, getHeight()-26);
        addObject(aim,paddle.getX(),paddle.getY()-20);
        // Add the score board into the world
        addObject(scoreBoard,658,511);
        // Add the level counter to world
        addObject(levelNum,575,511);
        // Create a new fader for this class and add it to the world
        fader = new Fader();
        addObject (fader, 400, 300);
        // import menu
        menu = new CoverPage();
        menu.setImage(config.get(GameContext.MENU_IMG));
////        menu.setImage("menu.png");
//
//        ball.setImage("ball.png");
        addObject (menu, 350,260);

        addObject(musicplayer,680,460);
        addObject(volumeup,680,430);
        addObject(volumedown,680,490);

        //Add life "bar" into world
        addObject( live1, 23, 510);
        addObject( live2, 69, 510);
        addObject( live3, 115, 510);
    }

    // each act check for death, mouse input and whether to create new level
    public void act()
    {
        checkLevel();
        checkMouse();
        checkLives();
        
        controller.polling();
    }

    // checks if player looses life
    public  void checkLives()
    {
        // Whenever player lose life, remove corresponding life bar
        if (lives == 3)
        {
            removeObject(live3);
        }
        if (lives == 2)
        {
            removeObject(live2);
        }
        if (lives == 1)
        {
            removeObject(live1);
            // End game. Remove Actors from world.
            backgroundMusic.stop();
            // play game over sound
            gameOverSound();
            // Display GameOver screen
            gameOver = new GameOver();
            addObject (gameOver, 350,260);

            removeObjects(getObjects(Smoke.class)); 
            removeObjects(getObjects(BasicBall.class));

            removeObjects(getObjects(Pointy.class));
            // end game when gameover sound is finished playing
            if (played)
            {
                Greenfoot.stop();
            }
        }
    }

    // return boolean after gameover sound played
    public void gameOverSound()
    {
        Greenfoot.playSound("gameOver.wav");
        played=true;
    }

    // subtract 1 from total life and add a new ball to the world
    public void takeLife()
    {
        replaceBall();
        lives--;
    }

    // reward points according to destroyed brick
    public void addPoints(int points)
    {
        score+=points;
        // refreshes counter display for score
        scoreBoard.update(score);
    }

    // checks for player input from mouse
    public void checkMouse()
    {
        // send cursor value to mouse variable
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int changeX;
        int mouseX;
        int mouseY;
        // check don't exceed left and right border of background
        // don't move paddle before player shoots
        if (Greenfoot.mouseClicked(null))
        {
            // once clicked, remove menu
            removeObject(menu);
            // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.
            clickMenu++;
        }

        // if ball has launched, move paddle according to user input
        if (start)
        {
            if (Greenfoot.mouseMoved(null) && mouse.getX() > (paddle.getImage().getWidth())/3 && mouse.getX() <  (getWidth()+5) - paddle.getImage().getWidth()/2)
            {
                // calculate difference for actual magnitude moved
                changeX = mouse.getX()-paddle.getX();
                // move paddle accordingly
                movePaddle(changeX);
            }
        }

        // boolean does NOT work. Since the click from the menu will meet this statement. As a result, ball launches immediatly after menu screen.
        if (clickMenu>2)
        {
            if(paddle.haveBall() && Greenfoot.mouseClicked(null)&& !Greenfoot.mouseClicked(musicplayer)
                    && !Greenfoot.mouseClicked(volumeup) && !Greenfoot.mouseClicked(volumedown))
            {
                // release ball
                start = true;
                mouseX = mouse.getX();
                mouseY= mouse.getY();
                // launches ball according to angle of launch
                launchBall(mouseX,mouseY);
                // removes pointer
                removeObject(aim);
            }
            if(Greenfoot.mouseClicked(musicplayer)){
                if(backgroundMusic.isPlaying()){
                    backgroundMusic.pause();
                }
                else{
                    backgroundMusic.playLoop();
                    backgroundMusic.setVolume(volume);
                }
            }
            if(Greenfoot.mouseClicked(volumeup) && backgroundMusic.isPlaying() ){
                volume = volume <= 95 ? volume+5 : volume;
                backgroundMusic.setVolume(volume);
                volumeup.update(volume);
                volumedown.update(volume);

            }
            if(Greenfoot.mouseClicked(volumedown) && backgroundMusic.isPlaying()){

               volume = volume >= 5? volume-5 : volume;
               backgroundMusic.setVolume(volume);
               volumeup.update(volume);
               volumedown.update(volume);
            }
        }

    }
    // move paddle accordingly
    public void movePaddle(int distance)
    {
        paddle.moveMe(distance);
    }

    // checks to see if start new level
    public void checkLevel()
    {
        if(getObjects(Brick.class).isEmpty())
        {
            // remove ball from world. Reset into original location. removeObject(ball); does NOT work.
            removeObjects(getObjects(BasicBall.class));

            // reset to original location
            resetPosition();
            // increase level by 1 and call upon next level.
            level++;
            nextLevel();
        }
    }

    // create new level
    public void nextLevel()
    {
        // fader effect. Consume screen
        fader = new Fader();
        addObject (fader, 400, 300);

        levelNum.update(level);
        fader.fadeBackIn();

        currentState.setStage(GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.HARD));
        render(currentState);
//        stageLoader.load();

    }

    // launching ball to commence game
    public void launchBall(int mouseX, int mouseY)
    {
        paddle.releaseBall(mouseX,mouseY);
    }

    public void replaceBall()
    {
        // call method from paddle to create new ball Actor into world
        paddle.newBall();
    }

    // resets paddle, ball to original position after new level is called
    public void resetPosition()
    {
        start = false;
        paddle.setLocation(getWidth()/2, getHeight()-25);
        // BALL setlocation
        // create new ball since old ball was removed from world.
        replaceBall();
        addObject(aim,paddle.getX(),paddle.getY()-20);
    }

    // resets paddle, ball to original position after life lost
    public void getStartAgain()
    {
        start = false;

        paddle.setLocation(getWidth()/2, getHeight()-25);
        addObject(aim,paddle.getX(),paddle.getY()-20);

    }

    /**
     * @Author Jiaqi Qin
     * @param newActor check if newActor intersects with other actors already existed in the world
     * @param cls class of all other actors to check
     * @param <T> T should be subclasses of Actor
     * @return boolean
     */
    public <T extends Actor> boolean hasIntersectingActors(T newActor, Class cls){
        List<T> actors = getObjects(cls);
        for (T actor: actors) {
            if (actor.equals(newActor))
                continue;
            Rectangle newRect = new Rectangle(newActor.getX(), newActor.getY(), newActor.getImage().getWidth(), newActor.getImage().getHeight());
            Rectangle rect = new Rectangle(actor.getX(), actor.getY(), actor.getImage().getWidth(), actor.getImage().getHeight());
            if (rect.intersects(newRect)) return true;
        }
        return false;
    }

    @Override
    public GameProgress save() {
//        GameProgressStorage.getInstance().save(new GameProgress(getCurrentGameState()));
        return null;

    }

    @Override
    public void restore(GameProgress progress) {
//        setCurrentGameState(progress.getState());
    }
}
