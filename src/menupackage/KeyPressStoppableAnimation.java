package menupackage;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * Class Name: KeyPressStoppableAnimation.
 */
public class KeyPressStoppableAnimation implements Animation {

    //members
    private KeyboardSensor keyboardSensor;
    private Animation animation;
    private String key;
    private boolean stop;
    private boolean isAlreadyPressed;
    private boolean ignoreNextPress;

    /**
     * Constructor.
     * @param keyboardSensor - keyboard
     * @param key - pressed key
     * @param animate - animation to stop.
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboardSensor, String key, Animation animate) {

        this.keyboardSensor = keyboardSensor;
        this.key = key;
        this.animation = animate;
        this.isAlreadyPressed = true;
        this.ignoreNextPress = false;
        this.stop = false;

    }

    /**
     * Function Name: shouldStop.
     * @return if the animate should stop
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: stop draw the animation.
     * @param ds - the surface
     * @param dt - 1/fps
     */
    public void doOneFrame(DrawSurface ds, double dt) {
        if (this.isAlreadyPressed) {
            this.ignoreNextPress = this.keyboardSensor.isPressed(this.key);
            this.isAlreadyPressed = false;
        }

        this.animation.doOneFrame(ds, dt);
        if (this.keyboardSensor.isPressed(this.key)) {
            if (!this.ignoreNextPress) {
                this.stop = true;
            }
        } else {
            this.ignoreNextPress = false;
        }

    }


}
