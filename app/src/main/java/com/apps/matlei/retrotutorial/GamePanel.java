package com.apps.matlei.retrotutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.apps.matlei.retrotutorial.gameobjects.RectPlayer;

/**
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private RectPlayer player;
    private Point playerPoint;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        // instantiate Player; Color.rgb = static class, takes in the red, green, and blue value of a color & convert to corresponding integer
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(150, 150);

        // now we need to handle the portion in which this point changes position corresponding to where we touch

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        // start our game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // it may take several times before it works -> we keep looping til it works
        while (retry) {
            try {
                // stop our game loop
                thread.setRunning(false);
                // finish running the thread and terminate it
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // manage our touch inputs
        // switch case all the different Action Types:
        switch (event.getAction()) {
            // case without breaking: we want both cases feed into the same thing
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY()); // just set the player to where the finger touched the screen

        }


        return true; // returning false might give the app reasons not to detect our touch event,
        // but we just want to detect Every touch, so we just return True.
        // return super.onTouchEvent(event);
    }

    /**
     * Update our game frame by frame,
     * add 2 to y & x value of the gameobject (game character)
     */
    public void update() {
        player.update(playerPoint); // playerPoint = where it should be
    }

    /**
     * Method to draw evertyhing in our game onto that canvas & display it.
     * look at what the game object coordinates are right now and draw it (the character) onto the screen
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // fill the screen with color --> make the canvas solid yellow except for our player then
        canvas.drawColor(Color.YELLOW);

        // draw the player on the screen
        player.draw(canvas);
    }

}
