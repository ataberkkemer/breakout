#include <android/log.h>

#include "StrongBrick.h"

#define TAG "StrongBrickCpp"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

StrongBrick::StrongBrick(int row, int column, int width, int height)
        : Brick(row, column, width, height) {
    maxHits = 3;
    updateColor();
}

void StrongBrick::hit() {
    hitCount++;
    if (hitCount >= maxHits) {
        setInvisible();
    } else {
        updateColor();
    }
    LOGD("StrongBrick hit - hitCount: %d", hitCount);
}

void StrongBrick::updateColor() {
    switch (hitCount) {
        case 0:
            currentColor = 0xFFFF0000;  // RED
            break;
        case 1:
            currentColor = 0xFFFFFF00;  // YELLOW
            break;
        case 2:
            currentColor = 0xFF00FF00;  // GREEN
            break;
    }
}
