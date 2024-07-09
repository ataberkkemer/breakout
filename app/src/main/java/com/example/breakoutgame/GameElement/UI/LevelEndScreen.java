package com.example.breakoutgame.GameElement.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class LevelEndScreen {
    private String message;
    private int screenX;
    private int screenY;
    private RectF restartButton;
    private RectF quitButton;

    public LevelEndScreen(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        float buttonWidth = screenX / 3;
        float buttonHeight = screenY / 10;
        restartButton = new RectF(screenX / 4, screenY / 2 + 100, screenX / 4 + buttonWidth, screenY / 2 + 100 + buttonHeight);
        quitButton = new RectF(screenX / 4 * 2, screenY / 2 + 100, screenX / 4 * 2 + buttonWidth, screenY / 2 + 100 + buttonHeight);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void draw(Canvas canvas) {
        if (canvas != null && message != null) {
            Paint paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(90);
            canvas.drawText(message, 10, screenY / 2, paint);

            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawRect(restartButton, paint);
            canvas.drawRect(quitButton, paint);

            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(60);
            canvas.drawText("Restart", restartButton.left + 20, restartButton.top + 70, paint);
            canvas.drawText("Quit", quitButton.left + 60, quitButton.top + 70, paint);
        }
    }

    public boolean isRestartButtonPressed(float x, float y) {
        return restartButton.contains(x, y);
    }

    public boolean isQuitButtonPressed(float x, float y) {
        return quitButton.contains(x, y);
    }
}
