package interfaces;

import biuoop.DrawSurface;
import geomitryprimitives.Rectangle;

/**
 * Interface Name: Drawer.
 */
public interface Drawer {
    /**
     * Function Name:drawAt.
     * Function Operation: draw the rectangle/block
     * @param d - the surface
     * @param rect - the rectangle
     */
    void drawAt(DrawSurface d, Rectangle rect);
}
