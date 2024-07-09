#include <android/log.h>
#include <jni.h>

#include <cstdlib>

#include "Ball.h"

#define TAG "BallCpp"
#define LOG(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

Ball::Ball(int screenX, int screenY) {
    xVelocity = 200;
    yVelocity = -400;
    reset(screenX, screenY);
    LOG("Ball created with initial position - left: %f, top: %f, right: %f, "
        "bottom: %f",
        left, top, right, bottom);
}

void Ball::update(float fps) {
    if (fps > 0) {
        left += xVelocity / fps;
        top += yVelocity / fps;
        right = left + ballWidth;
        bottom = top - ballHeight;
        LOG("Ball position updated - left: %f, top: %f, right: %f, bottom: %f, "
            "fps: %f",
            left, top, right, bottom, fps);
    } else {
        LOG("Invalid fps: %f", fps);
    }
}

void Ball::reverseYVelocity() {
    yVelocity = -yVelocity;
    LOG("Reversed Y Velocity");
}

void Ball::reverseXVelocity() {
    xVelocity = -xVelocity;
    LOG("Reversed X Velocity");
}

void Ball::setRandomXVelocity() {
    if (rand() % 2 == 0) {
        reverseXVelocity();
    }
    LOG("Random X Velocity Set");
}

void Ball::clearObstacleY(float y) {
    bottom = y;
    top = y - ballHeight;
    LOG("Cleared Obstacle Y at: %f", y);
}

void Ball::clearObstacleX(float x) {
    left = x;
    right = x + ballWidth;
    LOG("Cleared Obstacle X at: %f", x);
}

void Ball::reset(int screenX, int screenY) {
    left = screenX / 2;
    top = screenY - 20;
    xVelocity = 200;
    yVelocity = -400;
    right = left + ballWidth;
    bottom = top - ballHeight;

    LOG("Ball reset with position - left: %f, top: %f, right: %f, bottom: %f",
        left, top, right, bottom);
}

void Ball::increaseSpeed(float factor) {
    xVelocity *= factor;
    yVelocity *= factor;
    LOG("Increased speed by factor: %f", factor);
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeCreateBall(
        JNIEnv *env, jobject thiz, jint screenX, jint screenY) {
    return reinterpret_cast<jlong>(new Ball(screenX, screenY));
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeUpdate(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat fps) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->update(fps);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeReverseYVelocity(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->reverseYVelocity();
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeReverseXVelocity(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->reverseXVelocity();
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeSetRandomXVelocity(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->setRandomXVelocity();
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeClearObstacleY(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat y) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->clearObstacleY(y);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeClearObstacleX(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat x) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->clearObstacleX(x);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeReset(
        JNIEnv *env, jobject thiz, jlong ptr, jint screenX, jint screenY) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->reset(screenX, screenY);
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeGetLeft(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    return ball->left;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeGetTop(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    return ball->top;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeGetRight(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    return ball->right;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeGetBottom(
        JNIEnv *env, jobject thiz, jlong ptr) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    return ball->bottom;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_breakoutgame_GameElement_GameObject_Ball_nativeIncreaseSpeed(
        JNIEnv *env, jobject thiz, jlong ptr, jfloat factor) {
    Ball *ball = reinterpret_cast<Ball *>(ptr);
    ball->increaseSpeed(factor);
}
