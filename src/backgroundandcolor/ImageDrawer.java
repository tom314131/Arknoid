package backgroundandcolor;

import biuoop.DrawSurface;
import geomitryprimitives.Rectangle;
import interfaces.Drawer;

import java.awt.Image;

/**
 * Class Name: ImageDrawer.
 */
public class ImageDrawer implements Drawer {
    private Image image;

    /**
     * Constructor.
     * @param img - the image
     */
    public ImageDrawer(Image img) {
        this.image = img;
    }

    /**
     * Function Name: drawAt.
     * Function Operation: draw the image.
     * @param d    - the surface
     * @param rect - the rectangle
     */
    @Override
    public void drawAt(DrawSurface d, Rectangle rect) {
        d.drawImage((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(), this.image);
    }
}
