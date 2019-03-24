package sprites;

import java.util.ArrayList;
import java.util.List;

import interfaces.Sprite;
import biuoop.DrawSurface;

/**
 * Class Name: SpriteCollection.
 */
public class SpriteCollection {
    private List<Sprite> sprites;
    private int fps;

    /**
     * Function Name: SpriteCollection.
     * Function Operation: create sprite List.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Function Name: addSprite.
     * Function Operation: add a sprite to the list.
     * @param s - sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Function Name: removeSprite.
     * Function Operation: remove a sprite from the list.
     * @param s - sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * Function Name: notifyAllTimePassed.
     * Function Operation: call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.sprites.size(); i++) {
            Sprite s = this.sprites.get(i);
            s.timePassed(1 / this.fps);
        }
    }

    /**
     * Function Name: drawAllOn.
     * Function Operation: draw all the sprite on the screen.
     * @param d - the surface
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            Sprite s = this.sprites.get(i);
            s.drawOn(d);
        }
    }

    /**
     * Function Name:drawAllOn.
     * Function Operation: set frame per second
     * @param framesPerSecond - the frame per second of the game
     */
    public void setTime(int framesPerSecond) {
        this.fps = framesPerSecond;
    }
}


