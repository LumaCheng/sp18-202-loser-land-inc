package com.loserland.context;


import com.loserland.worlds.MyWorld;

public class GameStageGenerator {

    private static final int BRICK_MARGIN_TOP = 30;
    private static final int BRICK_MARGIN_BOTTOM = 220;
    private static final int BRICK_MARGIN_LEFT = 50;
    private static final int BRICK_MARGIN_RIGHT = 50;

    private static final int BRICK_NUM_EASY = 30;
    private static final int BRICK_NUM_MEDIUM = 50;
    private static final int BRICK_NUM_HARD = 100;
    private static final double BRICK_PERCENTAGE_1 = 0.3;
    private static final double BRICK_PERCENTAGE_2 = 0.3;
    private static final double BRICK_PERCENTAGE_3 = 0.4;

    public static enum Difficulty{
        EASY, MEDIUM, HARD
    }
    private static GameStageGenerator instance = null;

    private GameStageGenerator(){}

    public static GameStageGenerator getInstance() {
        if (instance == null){
            synchronized (GameStageGenerator.class){
                if (instance == null){
                    instance = new GameStageGenerator();
                }
            }
        }
        return instance;
    }

    public GameStage createStage(Difficulty difficulty) {

        int brickNum = 0;
        GameStage stage = new GameStage();
        switch (difficulty){
            case EASY:
                brickNum = BRICK_NUM_EASY;
                break;
            case MEDIUM:
                brickNum = BRICK_NUM_MEDIUM;
                break;
            case HARD:
                brickNum = BRICK_NUM_HARD;
                break;
        }

        int num3 = (int) (brickNum * BRICK_PERCENTAGE_3);
        int num2 = (int) (brickNum * BRICK_PERCENTAGE_2);
        int num1 = brickNum - num3 - num2;
        for (int i = 0; i < num1; i++) {
            stage.addBrick(new GameBrick(1, randomX(), randomY()));
        }
        for (int i = 0; i < num2; i++) {
            stage.addBrick(new GameBrick(2, randomX(), randomY()));
        }
        for (int i = 0; i < num3; i++) {
            stage.addBrick(new GameBrick(3, randomX(), randomY()));
        }
        return stage;
    }

    private int randomX(){
        int low = BRICK_MARGIN_LEFT;
        int high = MyWorld.config.get(Integer.class, GameContext.WORLD_WIDTH) - BRICK_MARGIN_RIGHT;
        return (int) (Math.random() * (high - low)+ low);
    }

    private int randomY(){
        int low = BRICK_MARGIN_TOP;
        int high = MyWorld.config.get(Integer.class, GameContext.WORLD_HEIGHT) - BRICK_MARGIN_BOTTOM;
        return (int) (Math.random() * (high - low) + low);
    }
}
