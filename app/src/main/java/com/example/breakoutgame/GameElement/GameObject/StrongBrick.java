package com.example.breakoutgame.GameElement.GameObject;

import android.graphics.Color;

public class StrongBrick extends Brick {

    public StrongBrick(int row, int column, int width, int height) {
        super(row, column, width, height);
        this.maxHits = 3; // Strong brick breaks with three hits
        updateColor();
    }

    @Override
    protected void updateColor() {
        switch (hitCount) {
            case 0:
                currentColor = Color.RED;
                break;
            case 1:
                currentColor = Color.YELLOW;
                break;
            case 2:
                currentColor = Color.GREEN;
                break;
        }
        nativeSetColor(nativePtr, currentColor);
    }
}
