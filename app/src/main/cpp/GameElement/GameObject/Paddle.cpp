#include <jni.h>

#include "Paddle.h"

Paddle::Paddle(int screenX, int screenY) {
    x = screenX / 2;
    y = screenY - 20;
    left = x;
    top = y;
    right = x + length;
    bottom = y + height;
    paddleMoving = STOPPED;
}

void Paddle::update(float fps) {
    if (paddleMoving == LEFT) {
        x -= paddleSpeed / fps;
    }
    if (paddleMoving == RIGHT) {
        x += paddleSpeed / fps;
    }
    left = x;
    right = x + length;
}

void Paddle::setMovementState(int state) { paddleMoving = state; }

void Paddle::setX(float newX) {
    x = newX - length / 2;  // Center the paddle on the touched position
    left = x;
    right = x + length;
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeCreatePaddle(
        JNIEnv *env, jobject thiz, jint screenX, jint screenY) {
    return reinterpret_cast<jlong>(new Paddle(screenX, screenY));
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeUpdate(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat fps) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    paddle->update(fps);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeSetMovementState(
        JNIEnv *env, jobject thiz, jlong ptr, jint state) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    paddle->setMovementState(state);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeSetX(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat x) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    paddle->setX(x);
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeGetLeft(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    return paddle->left;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeGetTop(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    return paddle->top;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeGetRight(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    return paddle->right;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Paddle_nativeGetBottom(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Paddle *paddle = reinterpret_cast<Paddle *>(ptr);
    return paddle->bottom;
}
