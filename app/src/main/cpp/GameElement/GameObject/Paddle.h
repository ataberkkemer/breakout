#ifndef PADDLE_H
#define PADDLE_H

class Paddle {
public:
    float left, top, right, bottom;
    const float length = 130.0f;
    const float height = 20.0f;
    float x, y;
    const float paddleSpeed = 350.0f;

    const int STOPPED = 0;
    const int LEFT = 1;
    const int RIGHT = 2;
    int paddleMoving;

    Paddle(int screenX, int screenY);
    void update(float fps);
    void setMovementState(int state);

    void setX(float newX);
};

#endif // PADDLE_H
