package com.example.breakoutgame.GameElement.GameObject;

import android.graphics.Color;

public class Brick {
    protected long nativePtr;
    protected int hitCount;
    protected int maxHits;
    protected int currentColor;

    public float left, top, right, bottom;

    public Brick(int row, int column, int width, int height) {
        nativePtr = nativeCreateBrick(row, column, width, height);
        updatePosition();
        this.hitCount = 0;
        this.maxHits = 1; // Default brick breaks with one hit
        this.currentColor = Color.GRAY;
    }

    private native long nativeCreateBrick(
            int row, int column, int width, int height);
    public native void nativeSetInvisible(long ptr);
    public native boolean nativeGetVisibility(long ptr);
    public native float nativeGetLeft(long ptr);
    public native float nativeGetTop(long ptr);
    public native float nativeGetRight(long ptr);
    public native float nativeGetBottom(long ptr);
    public native void nativeSetColor(long ptr, int color);

    public void setInvisible() {
        nativeSetInvisible(nativePtr);
    }

    public boolean getVisibility() {
        return nativeGetVisibility(nativePtr);
    }

    public void updatePosition() {
        left = nativeGetLeft(nativePtr);
        top = nativeGetTop(nativePtr);
        right = nativeGetRight(nativePtr);
        bottom = nativeGetBottom(nativePtr);
    }

    public void hit() {
        hitCount++;
        if (hitCount >= maxHits) {
            setInvisible();
        } else {
            updateColor();
        }
    }

    protected void updateColor() {}

    public int getCurrentColor() {
        return currentColor;
    }

    static {
        System.loadLibrary("breakoutgame");
    }
}
