#ifndef BRICK_H
#define BRICK_H

class Brick {
public:
    float left, top, right, bottom;
    bool isVisible;
    int hitCount;
    int maxHits;
    int currentColor;

    Brick(int row, int column, int width, int height);
    virtual ~Brick() = default;

    void setInvisible();
    bool getVisibility();
    void setColor(int color);
    virtual void hit();
    virtual void updateColor();

protected:
    void setInitialColor();
};

#endif // BRICK_H
