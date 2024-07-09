#include <jni.h>

#include <vector>

#include "../GameElement/GameObject/Ball.h"
#include "../GameElement/GameObject/Brick.h"
#include "../GameElement/GameObject/Paddle.h"

class BreakoutGame {
public:
    Ball *ball;
    Paddle *paddle;
    std::vector<Brick *> bricks;

    BreakoutGame(int screenX, int screenY);
    ~BreakoutGame();
    void update(float fps);
};

BreakoutGame::BreakoutGame(int screenX, int screenY) {
    ball = new Ball(screenX, screenY);
    paddle = new Paddle(screenX, screenY);

    int brickWidth = screenX / 8;
    int brickHeight = screenY / 10;

    for (int column = 0; column < 8; ++column) {
        for (int row = 0; row < 3; ++row) {
            bricks.push_back(new Brick(row, column, brickWidth, brickHeight));
        }
    }
}

BreakoutGame::~BreakoutGame() {
    delete ball;
    delete paddle;
    for (auto brick : bricks) {
        delete brick;
    }
}

void BreakoutGame::update(float fps) {
    paddle->update(fps);
    ball->update(fps);

    for (auto brick : bricks) {
        if (brick->getVisibility() &&  // Simple collision check
            ball->left < brick->right && ball->right > brick->left &&
            ball->top < brick->bottom && ball->bottom > brick->top) {
            brick->setInvisible();
            ball->reverseYVelocity();
        }
    }

    if (ball->left < paddle->right && ball->right > paddle->left &&
        ball->top < paddle->bottom && ball->bottom > paddle->top) {
        ball->reverseYVelocity();
    }
}
