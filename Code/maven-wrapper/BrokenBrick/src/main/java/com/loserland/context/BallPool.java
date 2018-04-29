package com.loserland.context;

import com.loserland.actors.BasicBall;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;

import java.util.*;

public class BallPool {

    private static BallPool instance = new BallPool();
    private Map<String, BasicBall> pool;
    private Set<String> availableSet;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    private BallPool(){
        reset();
    }

    public static BallPool getInstance() {
        return instance;
    }

    public List<BasicBall> fetch(int quantity){
        List<BasicBall> list = new ArrayList<>();

        String[] availableIds = availableSet.toArray(new String[0]);

        for (int i = 0; i < quantity && i < availableIds.length; i++) {
            BasicBall ball = pool.get(availableIds[i]);
            ball.setImage(config.get(GameContext.currentBallImg.getKey()));
            list.add(ball);
            availableSet.remove(availableIds[i]);
        }
        return list;
    }

    public void revert(BasicBall ball){
        ball.reset();
        availableSet.add(ball.getReuseIdentifier());
    }

    public void reset() {
        pool = new HashMap<>();
        availableSet = new HashSet<>();
        for (int i = 0; i < Integer.parseInt(config.get(GameContext.BALL_POOL_SIZE)); i++){
            String id = UUID.randomUUID().toString();
            availableSet.add(id);
            pool.put(id, new BasicBall(id));
        }
    }
}
