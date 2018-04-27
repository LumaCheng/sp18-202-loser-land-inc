package com.loserland.context;

import com.loserland.actors.BasicBall;

import java.util.*;

public class BallPool {

    private static BallPool instance = new BallPool();
    private final int POOL_SIZE = 30;
    private Map<String, BasicBall> pool;
    private Set<String> availableSet;

    private BallPool(){
        pool = new HashMap<>();
        availableSet = new HashSet<>();
        for (int i = 0; i < POOL_SIZE; i++){
            String id = UUID.randomUUID().toString();
            availableSet.add(id);
            pool.put(id, new BasicBall(id));
        }
    }

    public static BallPool getInstance() {
        return instance;
    }


    public BasicBall fetch() {
        BasicBall ball = null;
        if (!availableSet.isEmpty()){
            for (String id: pool.keySet()){
                if (availableSet.contains(id)){
                    ball = pool.get(id);
                    availableSet.remove(id);
                    break;
                }
            }
        }
        return ball;
    }

    public void revert(BasicBall ball){
        availableSet.add(ball.getReuseIdentifier());
    }
}
