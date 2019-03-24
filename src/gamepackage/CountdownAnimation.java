package gamepackage;

import interfaces.Animation;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;

// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) seconds, before
// it is replaced with the next one.

/**
 * Class Name: CountdownAnimation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private long sleepFor;
    private GUI gui;

    /**
     * Function Name: CountdownAnimation.
     * Function Operation: Constructor
     * @param numOfSeconds - num of seconds
     * @param countFrom    - start counting from number to 1
     * @param gameScreen   - the sprites
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.sleepFor = (long) numOfSeconds / countFrom;
        this.gameScreen = gameScreen;
    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: draw the count down 3 2 1 Go in 2 seconds
     * @param d - the surface
     * @param dt - 1/fps
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        long currentTime = System.currentTimeMillis();
        long stopTime = currentTime + sleepFor;
        while (currentTime < stopTime) {
            currentTime = System.currentTimeMillis();
        }
        if (!this.shouldStop()) {
            if (this.countFrom > 0) {
                d.setColor(Color.BLUE.brighter().brighter());
                d.drawText(348, 375, String.valueOf(this.countFrom), 60);
                d.setColor(Color.BLACK);
                d.drawText(350, 378, String.valueOf(this.countFrom), 60);
            } else {
                d.setColor(Color.BLUE.brighter().brighter());
                d.drawText(350, 375, "GO", 60);
                d.setColor(Color.BLACK);
                d.drawText(350, 378, "GO", 60);
            }
            this.countFrom--;
        }
    }

    /**
     * Function Name: shouldStop.
     * @return false if the countFrom + 2 reach to 0 adn ture otherwise.
     */
    public boolean shouldStop() {
        if (this.countFrom + 2 > 0) {
            return false;
        }
        return true;
    }

    /**
     * Function Name: setGui.
     * Function Operation: set the gui of the game in order to use it later.
     * @param g - gui
     */
    public void setGui(GUI g) {
        this.gui = g;
    }
}
