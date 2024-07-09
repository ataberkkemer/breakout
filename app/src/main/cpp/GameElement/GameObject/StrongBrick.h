#ifndef STRONG_BRICK_H
#define STRONG_BRICK_H

#include "Brick.h"

class StrongBrick : public Brick {
public:
    StrongBrick(int row, int column, int width, int height);
    void hit() override;
    void updateColor() override;
};

#endif // STRONG_BRICK_H
