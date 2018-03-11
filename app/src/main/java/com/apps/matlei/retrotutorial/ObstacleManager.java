package com.apps.matlei.retrotutorial;

import android.graphics.Canvas;

import com.apps.matlei.retrotutorial.gameobjects.Obstacle;

import java.util.ArrayList;

/**
 * Manager for moving the objects the player has to avoid down the screen.
 * Keep instantiating obstacle objects as they go off-screen
 *
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
public class ObstacleManager {
    // Note: higher index in arraylist = lower on screen = higher y value

    // store the obstacles as an arraylist
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;


    /**
     * @param playerGap      width of gap for player to fit through
     * @param obstacleGap    some room for the player to move around
     * @param obstacleHeight height for the obstacle rectangles
     * @param color          obstacles' color
     */
    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    /**
     * The region we fill with obstacles has to be larger than our screen height, four thirds of it.
     * The larger the index in the list => the further down on the screen the obstacle is.
     */
    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        // keep generateing obstacles  as long as it has not gone onto the screen yet
        // also generate a "random" gap for the player to go thru everytime => random x value for that start
        while (currY < 0) {
            // make sure there is room on the right of the random xValue where the player can fit in: generate random start-x
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    /**
     * Generate and add obstacles to the screen based on time = frame-rate independent.
     * Note: the highest index: if its top is below the Screen  => then generate a new one which is right At the top of the screen.
     */
    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        // scaling speed: how much is it moving per second
        float speed = Constants.SCREEN_HEIGHT / 10000.0f; // --> 10 seconds to move down the whole screen
        for (Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        // generate new obstacles as they go off screen
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            // generate a new start-x
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            // Note: the obstacle at index 0 is the highest on screen (lower index => higher on screen)

            // Note: the obstacle with the highest index: if its top is below the Screen, then
            // => generate a new one which is right At the top of the screen,
            // => remove the obstacle with the highest index from the arraylist.

            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
    }

    /**
     * Draws the obstacles from the arraylist onto the screen.
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
    }
}
