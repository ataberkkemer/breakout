package com.example.breakoutgame.GameEngine;

import android.media.SoundPool;

import com.example.breakoutgame.Data.CollisionResponse;
import com.example.breakoutgame.GameElement.GameObject.Ball;
import com.example.breakoutgame.GameElement.GameObject.Brick;
import com.example.breakoutgame.GameElement.GameObject.Paddle;

import java.util.List;

public class CollisionHandler {
    private SoundPool soundPool;
    private int soundCollideWall;
    private int soundCollidePaddle;
    private int soundDestroyBrick;
    private int soundCollideBrick;

    public CollisionHandler(SoundPool soundPool, int soundCollideWall, int soundCollidePaddle, int soundDestroyBrick, int soundCollideBrick) {
        this.soundPool = soundPool;
        this.soundCollideWall = soundCollideWall;
        this.soundCollidePaddle = soundCollidePaddle;
        this.soundDestroyBrick = soundDestroyBrick;
        this.soundCollideBrick = soundCollideBrick;
    }

    public CollisionResponse handleCollisions(Ball ball, Paddle paddle, List<Brick> bricks, int screenX, int screenY, int score, int lives) {
        for (Brick brick : bricks) {
            if (brick.getVisibility() &&
                    ball.left < brick.right && ball.right > brick.left &&
                    ball.top < brick.bottom && ball.bottom > brick.top) {
                brick.hit();
                if (!brick.getVisibility()) {
                    soundPool.play(soundDestroyBrick, 1, 1, 0, 0, 1);
                    score += 10;
                } else {
                    soundPool.play(soundCollideBrick, 1, 1, 0, 0, 1);
                }
                ball.reverseYVelocity();
                ball.increaseSpeed(1.05f);
            }
            brick.updatePosition();
        }

        // Check ball collision with paddle
        if (ball.left < paddle.right && ball.right > paddle.left &&
                ball.top < paddle.bottom && ball.bottom > paddle.top) {
            ball.reverseYVelocity();
            ball.increaseSpeed(1.025f); // Increase speed by 5%
            soundPool.play(soundCollidePaddle, 1, 1, 0, 0, 1);
        }

        // Check ball collision with bottom screen edge
        if (ball.bottom > screenY) {
            ball.reverseYVelocity();
            lives--;
            soundPool.play(soundCollideWall, 1, 1, 0, 0, 1);
        }

        // Check ball collision with top screen edge
        if (ball.top < 0) {
            ball.reverseYVelocity();
            soundPool.play(soundCollideWall, 1, 1, 0, 0, 1);
        }

        // Check ball collision with left screen edge
        if (ball.left < 0) {
            ball.reverseXVelocity();
            soundPool.play(soundCollideWall, 1, 1, 0, 0, 1);
        }

        // Check ball collision with right screen edge
        if (ball.right > screenX) {
            ball.reverseXVelocity();
            soundPool.play(soundCollideWall, 1, 1, 0, 0, 1);
        }

        return new CollisionResponse(score, lives);
    }
}
