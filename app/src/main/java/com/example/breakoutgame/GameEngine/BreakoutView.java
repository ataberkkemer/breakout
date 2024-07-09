package com.example.breakoutgame.GameEngine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.breakoutgame.GameElement.GameObject.Ball;
import com.example.breakoutgame.GameElement.GameObject.Brick;
import com.example.breakoutgame.GameElement.GameObject.StrongBrick;
import com.example.breakoutgame.Data.CollisionResponse;
import com.example.breakoutgame.GameElement.UI.LevelEndScreen;
import com.example.breakoutgame.GameElement.GameObject.Paddle;
import com.example.breakoutgame.R;

import java.util.ArrayList;
import java.util.List;

public class BreakoutView extends SurfaceView implements Runnable {

    private static final String TAG = "BreakoutView";

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playing;
    boolean paused = true;
    Canvas canvas;
    Paint paint;
    long fps;
    private long timeThisFrame;
    int screenX;
    int screenY;

    Ball ball;
    Paddle paddle;
    List<Brick> bricks;
    LevelEndScreen levelEndScreen;
    CollisionHandler collisionHandler;

    private SoundPool soundPool;
    private int soundCollideWall;
    private int soundCollidePaddle;
    private int soundDestroyBrick;
    private int soundCollideBrick;

    int score = 0;
    int lives = 3;

    public BreakoutView(Context context) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenX = size.x;
        screenY = size.y;

        ball = new Ball(screenX, screenY);
        paddle = new Paddle(screenX, screenY);
        bricks = new ArrayList<>();
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        CreateLevel(2, brickWidth, brickHeight);

        levelEndScreen = new LevelEndScreen(screenX, screenY);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        soundCollideWall = soundPool.load(context, R.raw.collide_wall, 1);
        soundCollidePaddle = soundPool.load(context, R.raw.collide_paddle, 1);
        soundDestroyBrick = soundPool.load(context, R.raw.destroy_brick, 1);
        soundCollideBrick = soundPool.load(context, R.raw.collide_brick, 1);

        collisionHandler = new CollisionHandler(soundPool, soundCollideWall, soundCollidePaddle, soundDestroyBrick, soundCollideBrick);
    }

    private void CreateLevel(int x, int brickWidth, int brickHeight) {
        for (int column = 0; column < 8; ++column) {
            for (int row = 0; row < 3; ++row) {
                if (row == x) {
                    bricks.add(new StrongBrick(row, column, brickWidth, brickHeight));
                } else {
                    bricks.add(new Brick(row, column, brickWidth, brickHeight));
                }
            }
        }
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            Log.d(TAG, "Game loop running");

            if (!paused) {
                update();
            }

            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        Log.d(TAG, "Update method called");
        ball.update(fps);
        paddle.update(fps);

        CollisionResponse response = collisionHandler.handleCollisions(ball, paddle, bricks, screenX, screenY, score, lives);
        score = response.getScore();
        lives = response.getLives();

        if (lives <= 0) {
            paused = true;
            levelEndScreen.setMessage("YOU HAVE LOST!");
        }

        if (score == bricks.size() * 10) {
            paused = true;
            resetGame();
        }
    }

    public void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.BLACK); // Set background to black
            paint.setColor(Color.argb(255, 255, 255, 255));

            canvas.drawRect(paddle.left, paddle.top, paddle.right, paddle.bottom, paint);
            canvas.drawRect(ball.left, ball.top, ball.right, ball.bottom, paint);

            for (Brick brick : bricks) {
                if (brick.getVisibility()) {
                    paint.setColor(brick.getCurrentColor());
                    canvas.drawRect(brick.left, brick.top, brick.right, brick.bottom, paint);
                }
            }

            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

            if (paused && lives == 0) {
                levelEndScreen.draw(canvas);
            }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (paused && lives == 0) {
                    if (levelEndScreen.isRestartButtonPressed(touchX, touchY)) {
                        resetGame();
                        paused = false;
                    } else if (levelEndScreen.isQuitButtonPressed(touchX, touchY)) {
                        ((Activity) getContext()).finish();
                    }
                } else {
                    paused = false; // Unpause the game when screen is touched
                    paddle.setX(touchX);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (!paused || lives != 0) {
                    paddle.setX(touchX);
                }
                break;

            case MotionEvent.ACTION_UP:
                // Optionally handle paddle stop or other logic
                break;
        }
        return true;
    }

    public void resetGame() {
        ball.reset(screenX, screenY);
        for (Brick brick : bricks) {
            brick.setInvisible();
        }
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;
        bricks.clear();
        CreateLevel(2, brickWidth, brickHeight);
        score = 0;
        lives = 3;
        paused = false; // Unpause the game when reset
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        paused = false; // Unpause the game when resumed
        gameThread = new Thread(this);
        gameThread.start();
    }
}
