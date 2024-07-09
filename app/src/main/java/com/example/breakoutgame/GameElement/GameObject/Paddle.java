package com.example.breakoutgame.GameElement.GameObject;

public class Paddle {
    private long nativePtr;

    public float left, top, right, bottom;

    public static final int STOPPED = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public Paddle(int screenX, int screenY) {
        nativePtr = nativeCreatePaddle(screenX, screenY);
    }

    private native long nativeCreatePaddle(int screenX, int screenY);
    public native void nativeUpdate(long ptr, float fps);
    public native void nativeSetMovementState(long ptr, int state);
    public native void nativeSetX(long ptr, float x);
    public native float nativeGetLeft(long ptr);
    public native float nativeGetTop(long ptr);
    public native float nativeGetRight(long ptr);
    public native float nativeGetBottom(long ptr);

    public void update(float fps) {
        nativeUpdate(nativePtr, fps);
        left = nativeGetLeft(nativePtr);
        top = nativeGetTop(nativePtr);
        right = nativeGetRight(nativePtr);
        bottom = nativeGetBottom(nativePtr);
    }

    public void setMovementState(int state) {
        nativeSetMovementState(nativePtr, state);
    }

    public void setX(float x) {
        nativeSetX(nativePtr, x);
        updatePosition();
    }

    private void updatePosition() {
        left = nativeGetLeft(nativePtr);
        top = nativeGetTop(nativePtr);
        right = nativeGetRight(nativePtr);
        bottom = nativeGetBottom(nativePtr);
    }

    static {
        System.loadLibrary("breakoutgame");
    }
}
