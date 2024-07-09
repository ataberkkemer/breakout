#include <android/log.h>
#include <jni.h>

#include "Brick.h"

Brick::Brick(int row, int column, int width, int height) {
    isVisible = true;
    hitCount = 0;
    maxHits = 1;  // Default for normal brick
    int padding = 1;
    left = column * width + padding;
    top = row * height + padding;
    right = column * width + width - padding;
    bottom = row * height + height - padding;
    setInitialColor();
}

void Brick::setInvisible() { isVisible = false; }

bool Brick::getVisibility() { return isVisible; }

void Brick::setColor(int color) { currentColor = color; }

void Brick::hit() {
    hitCount++;
    if (hitCount >= maxHits) {
        setInvisible();
    } else {
        updateColor();
    }
}

void Brick::updateColor() {
    // Default brick does not change color
}

void Brick::setInitialColor() {
    currentColor = 0xFF888888;  // Default color for normal brick (gray)
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeCreateBrick(
        JNIEnv *env, jobject thiz, jint row, jint column, jint width, jint height) {
    return reinterpret_cast<jlong>(new Brick(row, column, width, height));
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeSetInvisible(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    brick->setInvisible();
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeGetVisibility(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    return brick->getVisibility();
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeGetLeft(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    return brick->left;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeGetTop(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    return brick->top;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeGetRight(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    return brick->right;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeGetBottom(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    return brick->bottom;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Brick_nativeSetColor(
        JNIEnv *env, jobject thiz, jlong ptr, jint color) {
    Brick *brick = reinterpret_cast<Brick *>(ptr);
    brick->setColor(color);
}
