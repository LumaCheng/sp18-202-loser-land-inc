package com.loserland.context;

public class GameContext {

    /* Global constant */
    public static final String GAME_DEFAULT_CONFIG_FILENAME = "configs/default.properties";
    public static final String GAME_STAGE_CONFIG_FILENAME = "configs/stage.json";


    public static final String MENU_IMG = "page.menu.image";
    public static final String MAIN_IMG = "page.main.image";
    public static final String GAMEOVER_IMG = "page.end.image";
    public static final String SCORE_BG_IMG = "page.score.image";

    public static final String PLAYER_LIVES ="player.lives";

    public static final String PADDLE_WIDTH = "game.paddle.width";

    public static final String STAGE_PAUSE = "page.stage.pause";

    public static final String START_BUTTON = "page.start.button";
    public static final String START_HOVER = "page.start.hover";
    public static final String START_PRESSED = "page.start.pressed";

    public static final String LOAD_BUTTON = "page.load.button";
    public static final String LOAD_HOVER = "page.load.hover";
    public static final String LOAD_PRESSED = "page.load.pressed";

    public static final String SCORE_BUTTON = "page.score.button";
    public static final String SCORE_HOVER = "page.score.hover";
    public static final String SCORE_PRESSED = "page.score.pressed";

    public static final String ABOUT_BUTTON = "page.about.button";
    public static final String ABOUT_HOVER = "page.about.hover";
    public static final String ABOUT_PRESSED = "page.about.pressed";

    public static final String RESUME_BUTTON = "page.resume.button";
    public static final String RESUME_HOVER = "page.resume.hover";
    public static final String RESUME_PRESSED = "page.resume.pressed";

    public static final String SAVE_BUTTON = "page.save.button";
    public static final String SAVE_HOVER = "page.save.hover";
    public static final String SAVE_PRESSED = "page.save.pressed";

    public static final String EXIT_BUTTON = "page.exit.button";
    public static final String EXIT_HOVER = "page.exit.hover";
    public static final String EXIT_PRESSED = "page.exit.pressed";

    public static final String WORLD_WIDTH = "world.width";
    public static final String WORLD_HEIGHT = "world.height";
    public static final String WORLD_CELL_SIZE = "world.cell.size";

    public static String BALL_BOUNCE_SND = "ball.bounce.sound";
    public static String BALL_HIT_BRICK_SND = "ball.hitBrick.sound";
    public static String BALL_HIT_WALL_SND = "ball.hitWall.sound";
    public static String BALL_POWER_RATE = "ball.powerRate";
    public static String BALL_SMOKE_FREQ = "ball.smokeFreq";
    public static String BALL_INIT_X = "ball.initX";
    public static String BALL_INIT_Y = "ball.initY";
    public static String BALL_SPEED = "ball.speed";

    public static String SMOKE_IMG = "smoke.image";
    public static String FIREBALL = "fireBall.image";
    public static String FIRE_SMOKE = "fireSmoke.image";
    public static String EXPLOSION_IMG = "explosion.image";
    public static String PYROBLAST_BALL = "pyroblastBall.image";
    public static String PYROBLAST_SMOKE = "pyroblastSmoke.image";
    public static String PYROBLAST_EXPLODE_SND = "problastExplode.sound";
    public static String FIRE_BURN_SND = "fireBurn.sound";

    public static String POWER_LIST = "powerSquare.list";

    public static String NORMAL_PWR_IMG = "normalPower.image";
    public static String PYROBLAST_PWR_IMG = "pyroblastPower.image";
    public static String MULTIBALL_PWR_IMG = "multiballPower.image";
    public static String FIRE_PWR_IMG = "firePower.image";
    public static String SPEEDUP_PWR_IMG = "speedUpPower.image";
    public static String SPEEDDOWN_PWR_IMG = "speedDownPower.image";
    public static String LARGE_PAD_PWR_IMG = "largePaddlePower.image";
    public static String SMALL_PAD_PWR_IMG = "smallPaddlePower.image";
    public static String GOLDEN_EGG_PWR_IMG = "goldenEggPower.image";
    public static String GOLDEN_EGG_PWR_RATE = "goldenEggPower.rate";
    public static String GOLDEN_EGG_CUSTOM_PWR_RATE = "goldenEggCustomPowerRate";

    public static String SCOREBOARD_HEIGHT = "game.scoreboard.height";
    public static String SCOREBOARD_WIDTH = "game.scoreboard.width";

    public static final String GAME_BACKGROUND_MUSIC = "game.background.music";
    public static final String GAME_MENU_HIGHSCORE = "highscore.dat";
    public static final String PLAYER_PLAY_IMG = "player.play.image";
    public static final String PLAYER_PAUSE_IMG = "player.pause.image";
    public static final String PLAYER_SIZE = "player.size";
    public static final String VOLUME_SIZE = "volume.size";
    public static final String VOLUME_DEFAULT = "volume.default";
    public static final String VOLUME_UP_IMG = "volume.up.image";
    public static final String VOLUME_DOWN_IMG = "volume.down.image";
    public static final String HIGHSCORE_FONT_SIZE = "highscore.font.size";

    public static final String POINTY_IMG = "pointy.image";
    public static final String PADDLE_IMG = "paddle.image";
    public static final String PAUSE_STAGE_IMG = "pause.stage.image";

    public static final String BACK_BUTTON = "back.menu.button";

    public static final String MULTIBALL_IMAGE = "multiball.image";
    public static final String MULTIBALL_NUMBER = "multiball.number";
    public static final String GIFIMAGE_ANIME = "gif.image.anime";

    public enum BallType implements KeyEnum {
        NORMAL("ball.image"),
        SHIELD("shieldball.image"),
        POKEMON("pokemonball.image"),
        PIZZA("pizzaball.image"),
        SOCCER("soccerball.image"),
        BASEBALL("baseball.image"),
        BIRD("bird.image");

        private String key;

        public String getKey() {
            return key;
        }

        BallType(String key) {
            this.key = key;
        }
    }


    public enum ScoreLevel implements KeyEnum {
        SCORE_LEVEL_1("score.level1"),
        SCORE_LEVEL_2("score.level2"),
        SCORE_LEVEL_3("score.level3"),
        SCORE_LEVEL_4("score.level4"),
        SCORE_LEVEL_5("score.level5"),
        SCORE_LEVEL_6("score.level6");

        private String key;

        public String getKey() {
            return key;
        }

        ScoreLevel(String key) {
            this.key = key;
        }
    }

    public enum BrickLevel implements KeyEnum {
        BRICK_LV1("brick.level1.image"),
        BRICK_LV2("brick.level2.image"),
        BRICK_LV3("brick.level3.image"),
        BRICK_LV4("brick.level4.image"),
        BRICK_LV5("brick.level5.image"),
        BRICK_LV6("brick.level6.image");

        private String key;

        public String getKey() {
            return key;
        }

        BrickLevel(String key) {
            this.key = key;
        }
    }


    /* Global variable */
    public static BallType currentBallImg = BallType.NORMAL;
    public static String currentPaddleImg = "paddle.image";

}
