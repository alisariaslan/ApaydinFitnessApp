package com.pakachu.apaydinfitness.games.gapway;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private volatile MainThread mainThread;
    private Rect r = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private int score = 0;

    GamesActivity gamesActivity;

    public GamePanel(Context context, GamesActivity gamesActivity) {
        super(context);
        this.gamesActivity = gamesActivity;
        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.RED);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

        setFocusable(true);
    }

    public void resume() {

    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
    }

    public void exit() {
        gamesActivity.finish();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread = new MainThread(getHolder(), this);

        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
//        return super.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) motionEvent.getX(), (int) motionEvent.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 100) {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                    playerPoint.set((int) motionEvent.getX(), (int) motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true;
    }

    public void update() {
//        Log.e("game","gameover: "+gameOver);
//        Log.e("game","movingplayer: "+movingPlayer);

        if (!gameOver) {
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH / 10f);
        paint.setColor(Color.GREEN);
        drawTopLeftText(canvas, paint, "Score: 155");

        if (gameOver) {

            paint.setTextSize(Constants.SCREEN_WIDTH / 5f); // set font size
            paint.setColor(Color.RED);
            drawCenterText(canvas, paint, "Game Over");
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void drawTopLeftText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = r.width() / 2f - r.width() / 2f - r.left;
        float y = r.height() / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}
