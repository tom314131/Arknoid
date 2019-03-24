package menupackage;

import interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class Name: PauseScreen.
 */
public class PauseScreen implements Animation {

    //members
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Function Name: PauseScreen.
     * Function Operation: Constructor.
     * @param k - the keyboard sensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: show frame of stopping and stop showing that if
     *      the space key was pressed.
     * @param d - the surface of the game
     * @param dt - 1/fps
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * Function Name: shouldStop.
     * @return true if the game should continue and false if not
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
