package com.apps.matlei.retrotutorial.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * RectPlayer is a type of GameObject
 *
 * @author lei vs <matleivs@gmail.com>
 */
public class RectPlayer implements GameObject {

    // instance variables
    private Rect rectangle;
    private int color; // colors are represented by integers in Android Studio

    public RectPlayer(Rect rect, int color){
        this.rectangle = rect;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        // a Paint allows us to do a lot of things like font, color, etc. -> creating a custom style for Drawing sth.
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update() {

    }

    /**
     * Btw we're using coordinates a lot when dealing with canvasses:
     * top of the screen = zero y value, moving down the screen => y values are increasing.
     * That's why we Subtract to get to the top.
     *
     * @param point to be the center of the rectangle
     */
    public void update(Point point){
        // set(left x, top y, right x, bottom y values)
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2,
                point.x + rectangle.width()/2, point.y + rectangle.height()/2);

    }

    public Rect getRectangle(){
        return rectangle;
    }
}
