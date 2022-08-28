package com.pakachu.apaydinfitness.games.gapway;

import android.graphics.Canvas;

import java.util.ArrayList;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacleArrayList;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacleArrayList = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for (Obstacle ob : obstacleArrayList) {
            if (ob.playerCollide(player))
                return true;
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) ((float) Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacleArrayList.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
//        float speed = Constants.SCREEN_HEIGHT / 10000.0f;
        float speed = (float) (Math.sqrt(1 + (startTime - initTime) / 1000.0)) * Constants.SCREEN_HEIGHT / (10000.0f);
        for (Obstacle obstacle : obstacleArrayList) {
            obstacle.incrementY(speed * elapsedTime);
        }
        if (obstacleArrayList.get(obstacleArrayList.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacleArrayList.add(0, new Obstacle(obstacleHeight, color, xStart, obstacleArrayList.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacleArrayList.remove(obstacleArrayList.size() - 1);
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle obstacle : obstacleArrayList) {
            obstacle.draw(canvas);
        }
    }

}
