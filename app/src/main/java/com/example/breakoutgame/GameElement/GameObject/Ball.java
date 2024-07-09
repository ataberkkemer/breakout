package com.example.breakoutgame.GameElement.GameObject;

import android.util.Log;

public class Ball {
    private long nativePtr;
    private static final String TAG = "Ball";

    public float left, top, right, bottom;

    public Ball(int screenX, int screenY) {
        nativePtr = nativeCreateBall(screenX, screenY);
        left = nativeGetLeft(nativePtr);
        top = nativeGetTop(nativePtr);
        right = nativeGetRight(nativePtr);
        bottom = nativeGetBottom(nativePtr);
        Log.d(TAG, "Ball created with position - left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
    }

    private native long nativeCreateBall(int screenX, int screenY);
    public native void nativeUpdate(long ptr, float fps);
    public native void nativeReverseYVelocity(long ptr);
    public native void nativeReverseXVelocity(long ptr);
    public native void nativeSetRandomXVelocity(long ptr);
    public native void nativeClearObstacleY(long ptr, float y);
    public native void nativeClearObstacleX(long ptr, float x);
    public native void nativeReset(long ptr, int screenX, int screenY);
    public native float nativeGetLeft(long ptr);
    public native float nativeGetTop(long ptr);
    public native float nativeGetRight(long ptr);
    public native float nativeGetBottom(long ptr);
    public native void nativeIncreaseSpeed(long ptr, float factor);

    public void update(float fps) {
        if (fps > 0) {
            nativeUpdate(nativePtr, fps);
            left = nativeGetLeft(nativePtr);
            top = nativeGetTop(nativePtr);
            right = nativeGetRight(nativePtr);
            bottom = nativeGetBottom(nativePtr);
            Log.d(TAG, "Ball position updated - left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom + ", fps: " + fps);
        } else {
            Log.e(TAG, "Invalid fps: " + fps);
        }
    }

    public void reverseYVelocity() {
        nativeReverseYVelocity(nativePtr);
        Log.d(TAG, "Reversed Y Velocity");
    }

    public void reverseXVelocity() {
        nativeReverseXVelocity(nativePtr);
        Log.d(TAG, "Reversed X Velocity");
    }

    public void setRandomXVelocity() {
        nativeSetRandomXVelocity(nativePtr);
        Log.d(TAG, "Random X Velocity Set");
    }

    public void clearObstacleY(float y) {
        nativeClearObstacleY(nativePtr, y);
        Log.d(TAG, "Cleared Obstacle Y at: " + y);
    }

    public void clearObstacleX(float x) {
        nativeClearObstacleX(nativePtr, x);
        Log.d(TAG, "Cleared Obstacle X at: " + x);
    }

    public void reset(int screenX, int screenY) {
        nativeReset(nativePtr, screenX, screenY);
        left = nativeGetLeft(nativePtr);
        top = nativeGetTop(nativePtr);
        right = nativeGetRight(nativePtr);
        bottom = nativeGetBottom(nativePtr);
        Log.d(TAG, "Ball reset with position - left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
    }

    public void increaseSpeed(float factor) {
        nativeIncreaseSpeed(nativePtr, factor);
        Log.d(TAG, "Increased speed by factor: " + factor);
    }

    static {
        System.loadLibrary("breakoutgame");
    }
}
