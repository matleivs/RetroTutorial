package com.apps.matlei.retrotutorial.gameobjects;

import android.graphics.Canvas;

/**
 * Base definition for all the methods that will be required to be implemented by any game Object.
 * (All methods are abstract; declaration: anything that implements this interface needs to have that method.)
 *
 * @author lei vs <matleivs@gmail.com>
 */
public interface GameObject {

    /**
     * Draw the Player (or other GameObject) on the canvas.
     * @param canvas
     */
    void draw(Canvas canvas);

    void update();
}
