package com.apps.matlei.retrotutorial.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * These objects will move downstream the screen and the player has to evade them.
 *
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
public class Obstacle implements GameObject {

    private Rect rectangle;
    private int color;
    // The obstacle is like two rectangles with a gap in between it:
    private int startX;
    private int playerGap;

    public Obstacle(Rect rect, int color, int startX, int playerGap) {
        this.rectangle = rect;
        this.color = color;
        this.startX = startX;
        this.playerGap = playerGap;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * Method to check if the player is colliding with an obstacle
     *
     * @param player
     * @return true if collision
     */
    public boolean playerCollide(RectPlayer player){
        // later: fill in a player interface, so we can use all kinds of player objects not just rectplayers
        // check if the rectangle contains the top left/ top right/ bottom left/ bottom right coordinate of the player rectangle
        return ( rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().left, player.getRectangle().bottom)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().bottom));
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update() {

    }
}

