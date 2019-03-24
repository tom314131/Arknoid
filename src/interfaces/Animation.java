package interfaces;

import biuoop.DrawSurface;

/**
 * Interface Name: Animation.
 */
public interface Animation {
    /**
     * Function Name: doOneFrame.
     * Function Operation: draw one frame of the game
     * @param d - the surface of the game.
     * @param dt - 1/fps
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Function Name: shouldStop.
     * @return whether the game should stop or not (true or false)
     */
    boolean shouldStop();
}
