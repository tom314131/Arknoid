package backgroundandcolor;

import biuoop.DrawSurface;
import geomitryprimitives.Rectangle;
import interfaces.Drawer;

import java.awt.Color;

/**
 * Class Name: StrokeDrawer.
 */
public class StrokeDrawer implements Drawer {

    //member
    private Color color;

    /**
     * Constructor.
     * @param col - the color
     */
    public StrokeDrawer(Color col) {
        this.color = col;
    }

    /**
     * Function Name:drawAt.
     * Function Operation: draw the stroke on the rectangle
     * @param d    - the surface
     * @param rect - the rectangle
     */
    @Override
    public void drawAt(DrawSurface d, Rectangle rect) {
        d.setColor(this.color);
        d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
    }
}
