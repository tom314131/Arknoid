package indicators;

import interfaces.Sprite;
import biuoop.DrawSurface;
import gamepackage.Counter;
import gamepackage.GameLevel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.Polygon;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class Name: LivesIndicator.
 */
public class LivesIndicator implements Sprite {
    //members
    private Counter lives;
    private InputStream is;
    private boolean succeed;
    private Image image;

    /**
     * Function Name: LivesIndicator.
     * Function Operation: Constructor
     * @param l - lives that left
     */
    public LivesIndicator(Counter l) {
        this.lives = l;
        try {
            this.is = new BufferedInputStream(ClassLoader.getSystemClassLoader().
                    getResourceAsStream("background_images/bluecrocs.png"));
            this.succeed = true;
            this.image = ImageIO.read(is);
        } catch (IOException ex) {
            this.succeed = false;
        }
    }

    /**
     * Function Name: drawOn.
     * Function Operation: draw the lives
     * @param d - the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 80, 15);
        d.setColor(Color.BLACK);
        d.drawText(10, 12, "Lives :" + this.lives.getValue(), 15);
        for (int i = 0; i < this.lives.getValue(); i++) {

            if (!this.succeed) {
                int startX = 200;
                drawLives(startX + i * 60, 1, d);
            } else {
                int startX = 100;
                drawLives(startX + i * 20, 1, d);
            }
        }
    }

    /**
     * Function Name: timePassed.
     * @param dt - 1/fps
     */
    @Override
    public void timePassed(double dt) {
        return;
    }

    /**
     * Function Name: addToGame.
     * Function Operation: add the game level to the sprite lis
     * @param gameLevel - the game level
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * Function Name: getLives.
     * @return the number of lives
     */
    public Counter getLives() {
        return this.lives;
    }

    /**
     * Function Name: drawLives.
     * Function Operation: draw hearts as lives
     * @param x - x point of heart
     * @param y - y point of hart
     * @param d - the surface
     */
    public void drawLives(int x, int y, DrawSurface d) {
        if (this.succeed) {
            d.drawImage(x, y, this.image);
        } else {
            int[] xp = {x / 2 - 4, (x + 24) / 2 + 1, (x + 10) / 2 - 1};
            int[] yp = {(y + 14) / 2 - 1, (y + 14) / 2, (y + 30) / 2};
            d.setColor(Color.RED.darker());
            d.fillOval(x / 2 - 5, y / 2, 10, 10); //left circle
            d.fillOval((x + 5) / 2 + 2, (y + 5) / 2, 5, 5); //to cover middle spot
            d.fillOval((x + 6) / 2, (y / 2), 10, 10); //right circle
            Polygon polygon = new Polygon(xp, yp, xp.length); //bottom triangle
            d.fillPolygon(polygon);
        }
    }
}
