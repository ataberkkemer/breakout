#ifndef BALL_H
#define BALL_H

class Ball {
public:
    float left, top, right, bottom;
    float xVelocity, yVelocity;
    const float ballWidth = 10.0f;
    const float ballHeight = 10.0f;

    Ball(int screenX, int screenY);
    void update(float fps);
    void reverseYVelocity();
    void reverseXVelocity();
    void setRandomXVelocity();
    void clearObstacleY(float y);
    void clearObstacleX(float x);
    void reset(int screenX, int screenY);

    void increaseSpeed(float factor);
};

#endif  // BALL_H
