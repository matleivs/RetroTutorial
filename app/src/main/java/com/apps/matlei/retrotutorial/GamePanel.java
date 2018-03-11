package com.apps.matlei.retrotutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.apps.matlei.retrotutorial.gameobjects.RectPlayer;

/**
 * Game Panel class
 *
 * @author lei vs <matleivs@gmail.com>
 */
class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;


    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        // instantiate Player; Color.rgb = static class, takes in the red, green, and blue value of a color & convert to corresponding integer
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        // start position of the player: middle of the screen's width, and 3 /4 down of the screen's height
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        // to align the player to the playerPoint:
        player.update(playerPoint);


        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
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
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // manage our touch inputs
        // switch case all the different Action Types:
        switch (motionEvent.getAction()) {
            // formerly: case without breaking: we want both cases feed into the same thing
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    movingPlayer = true;
                }
                // show game over screen for 2 secs, before user can tap on screen and set gameover to false & reset game
//                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000){ <-- WIP this is not working, the app freezes at game over screen and never resets
                if (gameOver) {
                    reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer) {
                    // set the player to where the finger touched the screen
                    playerPoint.set((int) motionEvent.getX(), (int) motionEvent.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true; // returning false might give the app reasons not to detect our touch event,
        // but we just want to detect Every touch, so we just return True.
        // return super.onTouchEvent(event);
    }

    /**
     * Reset all the obstacles and move our player to a safe starting position.
     */
    private void reset() {
        //reset position of player back into starting position
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
        gameOver = false;
    }

    /**
     * Update our game frame by frame,
     * update the position of our gameobject (game character)
     * and update the positions of our obstacles = moving them down
     */
    public void update() {
        if (!gameOver) {
            player.update(playerPoint); // playerPoint = where it should be
            obstacleManager.update();
        }
        if (obstacleManager.playerCollide(player)) {
            gameOver = true;
            gameOverTime = System.currentTimeMillis();
        }
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
        // draw the obstacles on the screen
        obstacleManager.draw(canvas);

        // on GameOver, we wanna draw a "game over" text on the screen
        if (gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, getContext().getResources().getString(R.string.gameOver));
        }
    }


    // andreas1724 (white color): see {@link https://stackoverflow.com/questions/11120392/android-center-text-on-canvas}
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setColor(Color.rgb(255, 255, 255));
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}
