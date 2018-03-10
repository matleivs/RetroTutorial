package com.apps.matlei.retrotutorial;

import com.apps.matlei.retrotutorial.gameobjects.Obstacle;

import java.util.ArrayList;

/**
 * Manager for moving the objects the player has to avoid down the screen.
 * Keep instantiating obstacle objects as they go off-screen
 *
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
public class ObstacleManager {

    // Note: higher index = lower on screen = higher y value

    // store the obstacles as an arraylist
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;

    /**
     * @param playerGap   width of gap for player to fit through
     * @param obstacleGap some room for the player to move around
     */
    public ObstacleManager(int playerGap, int obstacleGap) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    /**
     * The region we fill with obstacles has to be larger than our screen height, four thirds of it.
     * The larger the index => the further down on the screen the obstacle is.
     */
    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        // keep generateing obstacles  as long as it has not gone onto the screen yet
        // also generate a "random" gap for the player to go thru everytime => random x value for that start
        while (obstacles.get(obstacles.size() - 1).getRectangle().bottom < 0) {
            // make sure there is room on the right of the random xValue where the player can fit in:
            int xStart =   (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));


        }
    }
}
