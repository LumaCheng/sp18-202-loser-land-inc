package com.loserland.context;

import com.loserland.actors.BasicBall;

import java.util.*;

public class BallPool {

    private static BallPool instance = new BallPool();
    private final int POOL_SIZE = 30;
    private Map<String, BasicBall> pool;
    private Set<String> availableSet;

    private BallPool(){
        reset();
    }

    public static BallPool getInstance() {
        return instance;
    }

    public List<BasicBall> fetch(int quantity){
        List<BasicBall> list = new ArrayList<>();

        String[] availableIds = availableSet.toArray(new String[0]);
        for (int i = 0; i < quantity && i < availableIds.length; i++){
            list.add(pool.get(availableIds[i]));
            availableSet.remove(availableIds[i]);
        }
        return list;
    }

//    public BasicBall fetch() {
//        BasicBall ball = null;
//        if (available()){
//            for (String id: pool.keySet()){
//                if (availableSet.contains(id)){
//                    ball = pool.get(id);
//                }
//            }
//        }
//        return ball;
//    }

    private boolean available() {
        return availableSet.size() > 1;
    }

    public void revert(BasicBall ball){
        availableSet.add(ball.getReuseIdentifier());
    }

    public void reset() {
        pool = new HashMap<>();
        availableSet = new HashSet<>();
        for (int i = 0; i < POOL_SIZE; i++){
            String id = UUID.randomUUID().toString();
            availableSet.add(id);
            pool.put(id, new BasicBall(id));
        }
    }
}
