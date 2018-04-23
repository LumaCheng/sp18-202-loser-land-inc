package com.loserland.worlds;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.context.GameStageGenerator;
import com.loserland.context.GameStageLoader;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.*;
import greenfoot.MouseInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Write a description of class com.loserland.MainWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */
public class MainWorld extends World
{
    // Declare variables, booleans and classes.
    private final int BRICKWIDTH = 45;
    private final int BRICKHEIGHT = 20;
    private final int VOFFSET = 12;
    private final int HOFFSET = 12;
    private Paddle paddle;
    private Fader fader;
    private MyWorld myWorld;
    private ContextController contextController = new ContextController();

    private ScoreBoard scoreBoard=new ScoreBoard();

    private Counter levelNum = new Counter();
    private Pointy aim = new Pointy();  

    private Musicplayer musicplayer;
    private Volumeup volumeup = new Volumeup();
    private Volumedown volumedown = new Volumedown();
    private HighScoreBoard highScoreBoard = new HighScoreBoard();
    private ManageScore managescore = new ManageScore();
    private PlayState playState;
    private PauseState pauseState;

    public static List<String> faceList = new ArrayList<>();
    private int faces = 0;

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
    private int clickMenu = 2;
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

    GameStageLoader gameStageLoader;

    /**
     * Constructor for objects of class com.loserland.MainWorld.
     *
     */
    public MainWorld()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(config.get(Integer.class, GameContext.WORLD_WIDTH), config.get(Integer.class, GameContext.WORLD_HEIGHT), config.get(Integer.class, GameContext.WORLD_CELL_SIZE));

        // Sets the order of display of Actors
        setPaintOrder(Fader.class,BasicBall.class,Pointy.class,Paddle.class, Smoke.class, Lives.class, ScoreBoard.class, Counter.class);

        //initialize UI components and put place
        initMusic();

        initUI();

        initScoreObserver();

        // clears screen instantly to show level 1
        fader.fadeBackIn();

        controller.addObserver(aim);
        controller.addObserver(paddle);

    }

    private void initScoreObserver(){

        managescore.attach(highScoreBoard);
        managescore.attach(scoreBoard);
        managescore.attach(contextController);


    }

    private void initMusic() {
        backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
        // play background music continuously

       // backgroundMusic.playLoop();
        musicplayer = new Musicplayer(backgroundMusic);
        playState = new PlayState();
        pauseState = new PauseState();
        //playState.doAction(musicplayer);

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

        addObject(musicplayer,680,460);
        addObject(volumeup,680,430);
        addObject(volumedown,680,490);

        gameStageLoader = new GameStageLoader(this);
        gameStageLoader.load();

        //Add life "bar" into world
        addObject( live1, 23, 510);
        addObject( live2, 69, 510);
        addObject( live3, 115, 510);

        faceList.add("face1.png");
        faceList.add("face2.png");
//        faceList.add("face3.png");
//        faceList.add("face4.png");
//        faceList.add("face5.png");
        faces = faceList.size();
        contextController.setMainWorld(this);
    }

    // each act check for death, mouse input and whether to create new level
    public void act()
    {
        checkLevel();
        checkMouse();
        checkLives();
        
        controller.polling();
    }

    public void setMyWorld(MyWorld myWorld){
        this.myWorld = myWorld;
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
            highScoreBoard.SaveScore();
            // play game over sound
            gameOverSound();
            // Display GameOver screen
            Greenfoot.setWorld(myWorld);
            addObject( live1, 23, 510);
            addObject( live2, 69, 510);
            addObject( live3, 115, 510);
            lives = 4;
            gameStageLoader.load();

            // end game when gameover sound is finished playing

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
        managescore.notifyObservers(score);
        // refreshes counter display for score
        //scoreBoard.update(score);
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

            // fixes bug. Instead of boolean, increase int by 1 to meet the if statement of ball launch.
            clickMenu++;
        }

        // if ball has launched, move paddle according to user input
        if (start)
        {
//            controller.addObserver(paddle);
        }

        // boolean does NOT work. Since the click from the menu will meet this statement. As a result, ball launches immediatly after menu screen.
        if (clickMenu>2)
        {
            if(paddle.haveBall() && Greenfoot.mouseClicked(null)&& !Greenfoot.mouseClicked(musicplayer)
                    && !Greenfoot.mouseClicked(volumeup) && !Greenfoot.mouseClicked(volumedown))
            {
                // release ball
                start = true;
                // launches ball according to angle of launch
                launchBall();
                // removes pointer
                removeObject(aim);
            }

            if(Greenfoot.mouseClicked(musicplayer)){
                if(backgroundMusic.isPlaying()){
                    pauseState.doAction(musicplayer);
                }
                else{
                    playState.doAction(musicplayer);
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
//    // move paddle accordingly
//    public void movePaddle(int distance)
//    {
//        paddle.moveMe(distance);
//    }

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
        gameStageLoader.load(GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.HARD));

//        // level 2 map
//        if (level==2)
//        {
//            // refreshes level counter
//            levelNum.update(2);
//            // fades the screen back in
//            fader.fadeBackIn();
//
//            /**
//             *  "C"
//             */
//
//            addObject( new Brick(1), 198, 90);
//            addObject( new Brick(4), 98, 43);
//            addObject( new Brick(6), 148, 226);
//            addObject( new Brick(4), 162, 49);
//            addObject( new Brick(1), 62, 90);
//            addObject( new Brick(4), 198, 196);
//            addObject( new Brick(4), 83, 214);
//            addObject( new Brick(4), 54, 175);
//            addObject( new Brick(4), 47, 128);
//
//            /**
//             * "O"
//             */
//            addObject( new Brick(3), 384, 53);
//            addObject( new Brick(3), 324, 53);
//            addObject( new Brick(1), 284, 90);
//            addObject( new Brick(1), 429, 90);
//            addObject( new Brick(3), 277, 134);
//            addObject( new Brick(5), 358, 213);
//            addObject( new Brick(3), 412, 184);
//            addObject( new Brick(3), 297, 185);
//            addObject( new Brick(3), 434, 137);
//
//            /**
//             * "M"
//             */
//            addObject( new Brick(2), 665, 53);
//            addObject( new Brick(2), 665, 215);
//            addObject( new Brick(1), 560, 90);
//            addObject( new Brick(1), 613, 90);
//            addObject( new Brick(6), 588, 135);
//            addObject( new Brick(2), 665, 105);
//            addObject( new Brick(2), 508, 163);
//            addObject( new Brick(2), 665, 159);
//            addObject( new Brick(2), 508, 215);
//            addObject( new Brick(2), 508, 53);
//            addObject( new Brick(2), 508, 111);
//
//        }
//        // level 3
//        else if (level==3)
//        {
//            // refreshes score counter
//            levelNum.update(3);
//            // fades screen back in
//            fader.fadeBackIn();
//
//            /**
//             *  "S"
//             */
//
//            addObject( new Brick(1), 124, 297);
//            addObject( new Brick(4), 172, 261);
//            addObject( new Brick(2), 179, 212);
//            addObject( new Brick(6), 141, 173);
//            addObject( new Brick(6), 75, 160);
//            addObject( new Brick(2), 44, 122);
//            addObject( new Brick(4), 57, 82);
//            addObject( new Brick(1), 94, 48);
//            addObject( new Brick(3), 145, 77);
//            addObject( new Brick(5), 177, 111);
//            addObject( new Brick(3), 63, 265);
//            addObject( new Brick(5), 48, 223);
//
//            /**
//             * "C"
//             */
//
//            addObject( new Brick(5), 391, 87);
//            addObject( new Brick(4), 344, 57);
//            addObject( new Brick(1), 286, 94);
//            addObject( new Brick(2), 274, 157);
//            addObject( new Brick(2), 273, 217);
//            addObject( new Brick(1), 294, 266);
//            addObject( new Brick(4), 355, 294);
//            addObject( new Brick(5), 408, 260);
//
//            /**
//             * "I"
//             */
//            addObject( new Brick(6), 612, 261);
//            addObject( new Brick(6), 555, 296);
//            addObject( new Brick(6), 610, 297);
//            addObject( new Brick(3), 576, 67);
//            addObject( new Brick(3), 645, 66);
//            addObject( new Brick(3),580, 179);
//            addObject( new Brick(6), 554, 262);
//            addObject( new Brick(3), 546, 118);
//            addObject( new Brick(3), 619, 119);
//            addObject( new Brick(3), 505, 67);
//        }
    }

    // launching ball to commence game
    public void launchBall()
    {
        paddle.releaseBall();
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
}
