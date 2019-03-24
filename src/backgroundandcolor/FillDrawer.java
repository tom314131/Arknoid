package backgroundandcolor;

import biuoop.DrawSurface;
import interfaces.Drawer;
import geomitryprimitives.Rectangle;

import java.awt.Color;

/**
 * Class Name: FillDrawer.
 */
public class FillDrawer implements Drawer {
    private Color color;

    /**
     * Constructor.
     * @param color - the color
     */
    public FillDrawer(Color color) {
        this.color = color;
    }

    /**
     * Function Name: drawAt.
     * Function Operation: drawing the rectangle
     * @param d    - the surface
     * @param rect - the rectangle
     */
    @Override
    public void drawAt(DrawSurface d, Rectangle rect) {
        d.setColor(this.color);
        d.fillRectangle((int) rect.getUpperLeft().getX(),
                (int) rect.getUpperLeft().getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }
}
