package com.loserland.worlds;

import com.loserland.actors.Back;
import com.loserland.actors.BasicBall;
import com.loserland.actors.Brick;
import com.loserland.actors.ContextController;
import com.loserland.actors.LevelLabel;
import com.loserland.actors.Fader;
import com.loserland.actors.HighScoreBoard;
import com.loserland.actors.Lives;
import com.loserland.actors.ManageScore;
import com.loserland.actors.MusicPlayer;
import com.loserland.actors.Paddle;
import com.loserland.actors.PlayState;
import com.loserland.actors.Pointy;
import com.loserland.actors.ScoreBoard;
import com.loserland.actors.Smoke;
import com.loserland.actors.StopState;
import com.loserland.actors.Volumedown;
import com.loserland.actors.Volumeup;
import com.loserland.actors.*;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.*;
import com.loserland.controller.Controller;
import com.loserland.controller.KeyBoardController;
import com.loserland.controller.MouseController;
import com.loserland.context.GameCheckPoint;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import greenfoot.World;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;


/**
 * Write a description of class com.loserland.MainWorld here.
 *
 * @author Jiaqi Qin
 * @version 2018-04-13
 */
public class MainWorld extends World implements IGameProgress
{
    // Declare variables, booleans and classes.

    private Fader fader;
    private MyWorld myWorld;
    private MainWorld mainWorld = this;
    private ContextController contextController = new ContextController();

    private ScoreBoard scoreBoard;
    private LevelLabel levelLabel;
    private Paddle paddle;

    private MenuButton resume ;
    private MenuButton save ;
    private MenuButton exit ;
    private ICommand resumeClick ;
    private ICommand saveClick ;
    private ICommand exitClick ;
    private PausePage pausePage ;
    private List<MenuButton> buttonsList = new ArrayList<>();

    private Pointy aim = new Pointy();  

    public MusicPlayer musicPlayer;
    private Volumeup volumeup;
    private Volumedown volumedown;
    private HighScoreBoard highScoreBoard = HighScoreBoard.getInstance();
    private ManageScore managescore = new ManageScore();
    private ManageScore managevolume = new ManageScore();
    private PlayState playState;
    private StopState stopState;
    private Back pause;

    // initalize background music, add was "backgroundMusic"
    GreenfootSound backgroundMusic;
    // boolean to determine if ball was launched
    private boolean start = false;
    // boolean to determine is gameOver music was played
    private boolean played = false;
    // instead of boolean uses integers to meet if statement for main menu. Fixes launch bug where ball launches immediatly after menu.
    //volume
    private int volume = config.get(Integer.class, GameContext.VOLUME_DEFAULT);

    // TODO: Using factory mode to initialize mouse
    private Controller mouse = new MouseController(this);
    private Controller keyboard = new KeyBoardController(this);


    //Configs
    private static ConfigFactory configFactory;
    public static Config config;

    static {
        configFactory = ConfigFactory.getInstance();
        config = configFactory.getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
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
        setPaintOrder(MenuButton.class, PausePage.class, Back.class, Fader.class,BasicBall.class,Pointy.class,Paddle.class, Smoke.class, Lives.class, ScoreBoard.class, LevelLabel.class);

        //initialize UI components and put place
        initMusic();
        initUI();

        //init game state
        currentState = new GameState();
        render(currentState);

        initScoreObserver();

        // clears screen instantly to show level 1
        fader.fadeBackIn();

        mouse.addObserver(aim);
        mouse.addObserver(paddle);

        keyboard.addObserver(aim);
        keyboard.addObserver(paddle);
    }

    private void render(GameState state) {
        List<Actor> actors = getObjects(Actor.class);
        for (Actor actor: actors){
            if (actor instanceof Storable){
                removeObject(actor);
            }
        }

        //render stage
        for (GameBrick gameBrick: state.getGameStage().getBricks()){
            Brick brick = gameBrick.restore();
            addObject(brick, gameBrick.getX(), gameBrick.getY() );

            if (hasIntersectingActors(brick, Brick.class)){
                removeObject(brick);
            }
        }

        //render paddle
        GamePaddle gamePaddle = state.getGamePaddle();
        renderPaddle(gamePaddle);

        //render LevelLabel
        GameLevel gameLevel = state.getGameLevel();
        renderLevel(gameLevel);

        //render ScoreBoard
        GameScore gameScore = state.getGameScore();
        renderScore(gameScore);

        //render LivesBar
        renderLivesBar(state.getGameLives());
    }

    private void renderPaddle(GamePaddle gamePaddle) {
        paddle.setWidth(gamePaddle.getPaddleWidth());
        paddle.setLocation(gamePaddle.getX(), gamePaddle.getY());
    }

    private void renderLevel(GameLevel gameLevel) {
        levelLabel = gameLevel.restore();
        addObject(levelLabel, gameLevel.getX(), gameLevel.getY());
    }

    private void renderScore(GameScore gameScore) {
        scoreBoard = gameScore.restore();
        addObject(scoreBoard, gameScore.getX(), gameScore.getY());
    }

    private void renderLivesBar(GameLives gameLives) {
        removeObjects(getObjects(Lives.class));
        int livesNum = gameLives.getLivesNum();
        int x = gameLives.getLives_x();
        int y = gameLives.getLives_y();
        int x_increment = gameLives.getLives_x_incremental();
        for (int i = 0; i < livesNum; i++){
            addObject(new Lives(), x, y);
            x += x_increment;
        }
    }

    private void initUI() {
        setBackground(config.get(GameContext.MAIN_IMG));

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
                        removeObject(pausePage);
                        removeObject(resume);
                        removeObject(save);
                        removeObject(exit);
                        resume();
                    }
                }
        ) ;

        saveClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        GameProgressManager.getInstance().add(mainWorld.save());
                        save.resetImage();
                    }
                }
        );

        exitClick.setReceiver(
                new IReceiver() {
                    public void doAction() {
                        Greenfoot.setWorld(myWorld);
                        musicPlayer.setCurrentState("stop");
                        myWorld.resetMainWorld();
                        exit.resetImage();
                    }
                }
        ) ;
        resume.setCommand(resumeClick);
        save.setCommand(saveClick);
        exit.setCommand(exitClick);

        pausePage = new PausePage();
        pausePage.setImage(config.get(GameContext.PAUSE_STAGE_IMG));

        paddle = new Paddle();
        addObject(paddle, getWidth()/2, getHeight()-26);

        // TODO: Padding need had consistent pos with mouse
        // read init points from config
        // add paddle into world
//        addObject(paddle, getWidth()/2, getHeight()-26);
        addObject(aim, 350,474);

        fader = new Fader();
        addObject (fader, 400, 300);
        // import menu
        pause = new Back();
        pause.setImage(config.get(GameContext.STAGE_PAUSE));
        addObject(pause, 25, 25);

        addObject(musicPlayer,680,460);
        addObject(volumeup,680,430);
        addObject(volumedown,680,490);

        contextController.setMainWorld(this);
    }

    public GameState getCurrentState() {
//        currentState = new GameState();
        GameStage stage = new GameStage();
        List<Brick> bricks = getObjects(Brick.class);
        for (Brick brick: bricks){
            stage.addBrick(brick.save());
        }
        currentState.setGameStage(stage);
//        currentState.setGamePaddle(paddle.save());
        currentState.getGamePaddle().setPaddleWidth(paddle.getImage().getWidth());
        return currentState;
    }

    private void initScoreObserver(){
        managescore.attach(highScoreBoard);
        managescore.attach(scoreBoard);
        managescore.attach(contextController);
    }

    private void initMusic() {
        musicPlayer = MusicPlayer.getInstance();
        playState = new PlayState();
        stopState = new StopState();
        volumeup = new Volumeup();
        volumedown = new Volumedown();
        playState.doAction();
        managevolume.attach(musicPlayer);
        managevolume.attach(volumeup);
        managevolume.attach(volumedown);

    }

    // each act check for death, mouse input and whether to create new level
    public void act()
    {
        checkLevel();
        checkMouse();
        checkLives();
        
        mouse.polling();
        keyboard.polling();
    }

    public void setMyWorld(MyWorld myWorld){
        this.myWorld = myWorld;
    }


    // checks if player looses life
    public  void checkLives()
    {
        if (currentState.getGameLives().getLivesNum() == 0){
            // End game. Remove Actors from world.
            highScoreBoard.SaveScore();
            // play game over sound
            gameOverSound();
            // Display GameOver screen
            myWorld.setGameOver();
            Greenfoot.setWorld(myWorld);
            myWorld.resetMainWorld();
            currentState.setGameStage(GameStageLoader.getInstance().load());
            render(currentState);
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
        currentState.getGameLives().setLivesNum(currentState.getGameLives().getLivesNum() - 1);
        renderLivesBar(currentState.getGameLives());
    }

    // reward points according to destroyed brick
    public void addPoints(int points)
    {
        currentState.getGameScore().setScore(currentState.getGameScore().getScore() + points);
        managescore.notifyObservers(currentState.getGameScore().getScore());
        renderScore(currentState.getGameScore());
    }

    // checks for player input from mouse
    public void checkMouse()
    {
        if(mouse.clicked(volumeup) && musicPlayer.isPlaying()){
            volume = volume <= 95 ? volume+5 : volume;
            managevolume.notifyObservers(volume);
        }
        else if(mouse.clicked(volumedown) && musicPlayer.isPlaying()){
            volume = volume >= 5? volume-5 : volume;
            managevolume.notifyObservers(volume);
        }

        else if(mouse.clicked(pause)){
            pause();
            addObject (pausePage, 350, 260);
            addObject (resume, 350,180);
            addObject (save, 350,250);
            addObject (exit, 350,320);
        }
        else if(Greenfoot.mouseClicked(musicPlayer)){
            musicPlayer.changeState();
        }

        else if(mouse.clicked(null)){
            start = true;
            // launches ball according to angle of launch
            launchBall();
            // removes pointer
            removeObject(aim);
        }
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
        removeObjects(getObjects(PowerSquare.class));

        // fader effect. Consume screen
        fader = new Fader();
        addObject (fader, 400, 300);

        fader.fadeBackIn();

        currentState.setGameStage(GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.HARD));
        currentState.getGameLevel().setLevel(currentState.getGameLevel().getLevel() + 1);
        levelLabel.setLevel(currentState.getGameLevel().getLevel());
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
        return new GameCheckPoint(getCurrentState());
    }

    @Override
    public void restore(GameCheckPoint checkPoint) {
        if (checkPoint == null) return;
        currentState = SerializationUtils.clone(checkPoint.getState());
        render(currentState);
    }

    public void pause() {
        List<BasicBall> ballList = getObjects(BasicBall.class);
        List<Smoke> smokeList = getObjects(Smoke.class);
        List<PowerSquare> powerSquareList = getObjects(PowerSquare.class);
        List<Paddle> paddleList = getObjects(Paddle.class);
        for(BasicBall ball : ballList) {
            ball.pause();
        }

        for(Smoke smoke : smokeList) {
            smoke.pause();
        }

        for(PowerSquare powerSquare : powerSquareList) {
            powerSquare.pause();
        }

        for(Paddle paddle : paddleList) {
            paddle.pause();
        }
    }

    public void resume() {
        List<BasicBall> ballList = getObjects(BasicBall.class);
        List<Smoke> smokeList = getObjects(Smoke.class);
        List<PowerSquare> powerSquareList = getObjects(PowerSquare.class);
        List<Paddle> paddleList = getObjects(Paddle.class);
        for(BasicBall ball : ballList) {
            ball.resume();
        }

        for(Smoke smoke : smokeList) {
            smoke.resume();
        }

        for(PowerSquare powerSquare : powerSquareList) {
            powerSquare.resume();
        }

        for(Paddle paddle :  paddleList) {
            paddle.resume();
        }
    }
}
