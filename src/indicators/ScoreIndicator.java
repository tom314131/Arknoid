package indicators;

import interfaces.Sprite;
import biuoop.DrawSurface;
import gamepackage.Counter;
import gamepackage.GameLevel;

import java.awt.Color;

/**
 * Class Name: ScoreIndicator.
 */
public class ScoreIndicator implements Sprite {

    //member
    private Counter score;

    /**
     * Function Name: ScoreIndicator.
     * Function Operation: Constructor
     * @param counter - the score
     */
    public ScoreIndicator(Counter counter) {
        this.score = counter;
    }

    /**
     * Function Name: drawOn.
     * Function Operation: draw the lives
     * @param d - the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 15);
        d.setColor(Color.BLACK);
        d.drawText(375, 12, "Score :" + this.score.getValue(), 15);
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
}
