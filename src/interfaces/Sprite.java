package interfaces;

import biuoop.DrawSurface;

/**
 * Class Name: Sprite.
 */
public interface Sprite {
    /**
     * Function Name: drawOn.
     * Function Operation: draw the sprite on screen.
     * @param d - the surface
     */
    void drawOn(DrawSurface d);

    /**
     * Function Name: timePassed.
     * Function Operation: notify the sprite that time has passed.
     * @param dt - 1/fps
     */
    void timePassed(double dt);
}

