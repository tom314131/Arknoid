package menupackage;

import interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class Name: EndGame.
 */
public class EndGame implements Animation {

    //members
    private boolean stop;
    private KeyboardSensor keyboard;
    private int lives;
    private int score;

    /**
     * Function Name: EndGame.
     * Function Operation: Constructor
     * @param k     - the keyboard
     * @param l     - the lives
     * @param score - the score
     */
    public EndGame(KeyboardSensor k, int l, int score) {
        this.stop = false;
        this.lives = l;
        this.keyboard = k;
        this.score = score;
    }

    /**
     * Function Game: doOneFrame.
     * Function Operation: draw win message if the wins and game over if he loses
     * @param d - the surface
     * @param dt - 1/fps
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (lives <= 0) {
            d.drawText(10, d.getHeight() / 2,
                    "Game Over. Your score is " + this.score, 32);
        } else {
            d.drawText(10, d.getHeight() / 2,
                    "You Won. Your score is " + this.score, 32);
        }
    }

    /**
     * Function Name: shouldStop.
     * @return true if the game should stop and false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
