package com.loserland.worlds;

import com.loserland.actors.BasicBall;
import com.loserland.actors.Brick;
import com.loserland.actors.ContextController;
import com.loserland.actors.Counter;
import com.loserland.actors.Exit;
import com.loserland.actors.Fader;
import com.loserland.actors.HighScoreBoard;
import com.loserland.actors.Lives;
import com.loserland.actors.ManageScore;
import com.loserland.actors.Musicplayer;
import com.loserland.actors.Paddle;
import com.loserland.actors.PlayState;
import com.loserland.actors.Pointy;
import com.loserland.actors.ScoreBoard;
import com.loserland.actors.Smoke;
import com.loserland.actors.StopState;
import com.loserland.actors.Volumedown;
import com.loserland.actors.Volumeup;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.*;
import com.loserland.controller.Controller;
import com.loserland.controller.MouseController;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import greenfoot.MouseInfo;
import org.apache.commons.lang3.SerializationUtils;
import java.awt.*;
import greenfoot.World;
import java.util.List;


/**
 * Write a description of class com.loserland.MainWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */
public class MainWorld extends World implements IGameProgress
{
    // Declare variables, booleans and classes.
    private Paddle paddle;
    private Fader fader;
    private MyWorld myWorld;
    private PauseWorld pauseWorld;
    private ContextController contextController = new ContextController();

    private ScoreBoard scoreBoard=new ScoreBoard();

    private Counter levelNum = new Counter();
    private Pointy aim = new Pointy();  

    private Musicplayer musicplayer;
    private Volumeup volumeup;
    private Volumedown volumedown;
    private HighScoreBoard highScoreBoard = HighScoreBoard.getInstance();
    private ManageScore managescore = new ManageScore();
    private ManageScore managevolume = new ManageScore();
    private PlayState playState;
    private StopState stopState;
    private Exit pause;

//    public static List<String> faceList = new ArrayList<>();
//    private int faces = 0;

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
    // initalize background music, add was "backgroundMusic"
    GreenfootSound backgroundMusic;
    // boolean to determine if ball was launched
    private boolean start = false;
    // boolean to determine is gameOver music was played
    private boolean played = false;
    // instead of boolean uses integers to meet if statement for main menu. Fixes launch bug where ball launches immediatly after menu.
    private int clickMenu = 2;
    //volume
    private int volume = config.get(Integer.class, GameContext.VOLUME_DEFAULT);

    // TODO: Using factory mode to initialize controller
    private Controller controller = new MouseController(this);
    //private Controller controller = new MouseController(this);


    //Configs
    private static ConfigFactory configFactory;
    public static Config config;

    static {
        configFactory = ConfigFactory.getInstance();
        config = configFactory.getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    }

    public GameState getCurrentState() {
        currentState = new GameState();
        GameStage stage = new GameStage();
        List<Brick> bricks = getObjects(Brick.class);
        for (Brick brick: bricks){
            stage.addBrick(brick.save());
        }
        currentState.setStage(stage);
        currentState.setScore(score);
        currentState.setLevel(level);
        currentState.setLives(lives);
        return currentState;
    }

    private GameState currentState;

    /**
     * Constructor for objects of class com.loserland.MainWorld.
     *
     */
    public MainWorld()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(config.get(Integer.class, GameContext.WORLD_WIDTH), config.get(Integer.class, GameContext.WORLD_HEIGHT), config.get(Integer.class, GameContext.WORLD_CELL_SIZE));

        // Sets the order of display of Actors
        setPaintOrder(Exit.class, Fader.class,BasicBall.class,Pointy.class,Paddle.class, Smoke.class, Lives.class, ScoreBoard.class, Counter.class);

        //initialize UI components and put place
        initMusic();

        //init game state
        currentState = new GameState();
        currentState.setStage(GameStageLoader.getInstance().load());
        render(currentState);

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
        //backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
        // play background music continuously

       // backgroundMusic.playLoop();
        musicplayer = Musicplayer.getInstance();
        playState = new PlayState();
        stopState = new StopState();
        volumeup = new Volumeup();
        volumedown = new Volumedown();
        playState.doAction(musicplayer);
        managevolume.attach(musicplayer);
        managevolume.attach(volumeup);
        managevolume.attach(volumedown);

    }

    private void initUI() {
        // create new paddle and ball
        paddle = new Paddle();

        // TODO: Padding need had consistent pos with controller
        // read init points from config
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
        pause = new Exit();
        pause.setImage(config.get(GameContext.STAGE_PAUSE));
        addObject(pause, 25, 25);

        addObject(musicplayer,680,460);
        addObject(volumeup,680,430);
        addObject(volumedown,680,490);

        //Add life "bar" into world
        addObject( live1, 23, 510);
        addObject( live2, 69, 510);
        addObject( live3, 115, 510);

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
    public void setPauseWorld(PauseWorld pauseWorld) { this.pauseWorld = pauseWorld;}
    public void stopMusic(){
        stopState.doAction(musicplayer);
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
            myWorld.setGameOver();
            Greenfoot.setWorld(myWorld);
            myWorld.resetMainWorld();
            addObject( live1, 23, 510);
            addObject( live2, 69, 510);
            addObject( live3, 115, 510);
            lives = 4;
//            gameStageLoader.load();
            currentState.setStage(GameStageLoader.getInstance().load());
            render(currentState);

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
        setLives(lives - 1);
    }

    // reward points according to destroyed brick
    public void addPoints(int points)
    {
        setScore(score + points);
    }

    public void setScore(int score){
        this.score = score;
        managescore.notifyObservers(score);
    }

    public void setLevel(int level) {
        this.level = level;
        levelNum.update(level);
    }

    // checks for player input from mouse
    public void checkMouse()
    {
        if(Greenfoot.mouseClicked(volumeup) && musicplayer.isPlaying()){
            volume = volume <= 95 ? volume+5 : volume;
            managevolume.notifyObservers(volume);
        }
        else if(Greenfoot.mouseClicked(volumedown) && musicplayer.isPlaying()){
            volume = volume >= 5? volume-5 : volume;
            managevolume.notifyObservers(volume);
        }

        else if(Greenfoot.mouseClicked(pause)){
            clickMenu = 2;
            Greenfoot.setWorld(pauseWorld);
        }
        else if(Greenfoot.mouseClicked(null)){
            start = true;
            // launches ball according to angle of launch
            launchBall();
            // removes pointer
            removeObject(aim);
        }

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
            nextLevel();
        }
    }

    // create new level
    public void nextLevel()
    {
        // fader effect. Consume screen
        fader = new Fader();
        addObject (fader, 400, 300);

        setLevel(level+1);

        fader.fadeBackIn();

        currentState.setStage(GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.HARD));
        currentState.setScore(score);
        currentState.setLevel(level);
        currentState.setLives(lives);
        render(currentState);

    }

    // launching ball to commence game
    public void launchBall()
    {
        if(paddle.haveBall())
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

    private void render(GameState state) {
        List<Actor> actors = getObjects(Actor.class);
        for (Actor actor: actors){
            if (actor instanceof Storable){
                removeObject(actor);
            }
        }

        setScore(state.getScore());
        setLevel(state.getLevel());
        setLives(state.getLives());

        //render stage
        for (GameBrick gameBrick: state.getStage().getBricks()){
            Brick brick = gameBrick.restore();
            addObject(brick, gameBrick.getX(), gameBrick.getY() );

            if (hasIntersectingActors(brick, Brick.class)){
                removeObject(brick);
            }
        }
    }

    private void setLives(int lives) {
        this.lives = lives;
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
    public GameCheckPoint save() {
        return new GameCheckPoint(currentState);
    }

    @Override
    public void restore(GameCheckPoint checkPoint) {
        if (checkPoint == null) return;
        currentState = SerializationUtils.clone(checkPoint.getState());
        render(currentState);

    }
}
