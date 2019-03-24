package gamepackage;

import interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Class Name: AnimationRunner.
 */
public class AnimationRunner {

    //members
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Function Name: AnimationRunner.
     * Function Operation: Constructor
     * @param g - the game
     * @param fps - frame per seconds
     */
    public AnimationRunner(GUI g, int fps) {
        this.gui = g;
        this.framesPerSecond = fps;
        this.sleeper = new Sleeper();
    }

    /**
     * Function Name: run.
     * Function Operation: run the animation of the game.
     * @param animation - the animation of the game.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame =  1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d, 1 / framesPerSecond);

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (animation.shouldStop()) {
                return;
            }
            gui.show(d);
        }
        long startTime = System.currentTimeMillis(); // timing
        DrawSurface d = gui.getDrawSurface();

        animation.doOneFrame(d, 1 / framesPerSecond);

        long usedTime = System.currentTimeMillis() - startTime;
        long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
        gui.show(d);
        if (milliSecondLeftToSleep > 0) {
            this.sleeper.sleepFor(milliSecondLeftToSleep);
        }
    }

    /**
     * Function Name: getFramesPerSecond.
     * @return frame per second
     */
    public int getFramesPerSecond() {
        return this.framesPerSecond;
    }
}
