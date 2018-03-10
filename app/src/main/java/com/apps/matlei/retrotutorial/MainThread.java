package com.apps.matlei.retrotutorial;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * The "meat" class of our game.
 *
 * @author Leïli Nikbin <nikbin@itemis.de>
 */
public class MainThread extends Thread {

    // most phones are able to run at least 30 fps (and well above) -> we wanna cap it, so not to (to limit) make unneccessary calls to the game loop
    public static final int FPS_MAX = 30;

    private  double fpsAverage;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    private int milliSecsPerSec = 1000;
    private int nanoSecsPerSec = 1000000;

    public MainThread(SurfaceHolder holder, GamePanel panel){
        super();
        this.surfaceHolder = holder;
        this.gamePanel = panel;
    }


    public void setRunning(boolean running){
        this.running = running;
    }


    @Override // a method contained within the thread class
    public void run(){
        long startTime;
        long timeMillis = milliSecsPerSec/ FPS_MAX;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        // what we're going for per frame:
        long targetTime = milliSecsPerSec/ FPS_MAX;

        // the meat of the game is in this while loop
        while (running) {
            startTime = System.nanoTime(); // a very accurate cloick built into the Android System hardware
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }

            } catch (Exception e){
                e.printStackTrace();
            } // even if an error occurs & we go into the catch block: still call the finally block (no matter if success or failure)
            finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e ){
                        e.printStackTrace();
                    }
                }

            }
            timeMillis = (System.nanoTime() - startTime)/nanoSecsPerSec;
            waitTime = targetTime - timeMillis;
            try {
                // if we finished the frame Earlier than the targetTime -> pause for that amount of wait time => capping the frameRate
                if(waitTime > 0) {

                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPS_MAX) {
                // milliSecsPerSec == a second -> divide by the time it took => get the framerate
                fpsAverage = milliSecsPerSec/((totalTime/frameCount)/nanoSecsPerSec);
                // reset the values so they will be sampled again the next 30 secs
                frameCount = 0;
                totalTime = 0;
                System.out.println(fpsAverage); // proof that we have created a game loop & it is running properly
            }
        }
    }
}
