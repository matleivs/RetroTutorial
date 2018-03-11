package com.apps.matlei.retrotutorial.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.apps.matlei.retrotutorial.Constants;

/**
 * These objects will move downstream the screen and the player has to evade them.
 *
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
public class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        // l,t,r,b.
        // The obstacle will be two rectangles with a gap in between it:
        this.rectangle = new Rect(0, startY, startX, startY + rectHeight);
        this.rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public Rect getRectangle() {
        return rectangle;
    }


    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    /**
     * Method to check if the player is colliding with an obstacle
     * (check if the rectangle contains the top left/ top right/ bottom left/ bottom right coordinate of the player rectangle)
     *
     * @param player
     * @return true if collision
     */
    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update() {

    }
}

