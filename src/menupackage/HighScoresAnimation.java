package menupackage;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * Class Name: HighScoresAnimation.
 */
public class HighScoresAnimation implements Animation {

    //members
    private HighScoresTable hst;
    private String ek;
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor.
     * @param scores         - the high table scores
     * @param endKey         - the key we press on keyboard
     * @param keyboardSensor - the keyboard
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor keyboardSensor) {
        this.hst = scores;
        this.ek = endKey;
        this.keyboard = keyboardSensor;
        this.stop = false;
    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: Draw the High score table on screen
     * @param d  - the surface of the game.
     * @param dt - 1/fps
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.drawText(254, 49, "High Scores", 45);
        d.setColor(Color.decode("#D50000"));
        d.drawText(255, 50, "High Scores", 45);
        d.setColor(Color.BLACK);
        d.drawText(256, 51, "High Scores", 45);

        d.drawText(254, 59, "_____________", 35);
        d.setColor(Color.decode("#D50000"));
        d.drawText(255, 60, "_____________", 35);
        d.setColor(Color.BLACK);
        d.drawText(256, 61, "_____________", 35);

        d.drawText(149, 109, "Player Name", 30);
        d.setColor(Color.decode("#D50000"));
        d.drawText(150, 110, "Player Name", 30);
        d.setColor(Color.BLACK);
        d.drawText(151, 111, "Player Name", 30);

        d.drawText(499, 109, "Score", 30);
        d.setColor(Color.decode("#D50000"));
        d.drawText(500, 110, "Score", 30);
        d.setColor(Color.BLACK);
        d.drawText(501, 111, "Score", 30);

        int space = 35;
        for (int i = 0; i < this.hst.getHighScores().size(); i++) {
            String name = hst.getHighScores().get(i).getName();
            String score = String.valueOf(hst.getHighScores().get(i).getScore());
            d.setColor(Color.black);
            d.drawText(149, 144 + space * i, name, 30);
            d.setColor(Color.decode("#42A5F5"));
            d.drawText(150, 145 + space * i, name, 30);
            d.setColor(Color.black);
            d.drawText(151, 146 + space * i, name, 30);

            d.drawText(549, 144 + space * i, score, 30);
            d.setColor(Color.decode("#42A5F5"));
            d.drawText(550, 145 + space * i, score, 30);
            d.setColor(Color.BLACK);
            d.drawText(551, 146 + space * i, score, 30);
        }

        String msg = "Press space to continue";
        d.setColor(Color.BLACK);
        d.drawText(224, 510, msg, 32);
        d.setColor(Color.decode("#304FFE"));
        d.drawText(225, 511, msg, 32);
        d.setColor(Color.BLACK);
        d.drawText(226, 512, msg, 32);
        try {
            File file = new File("highscores.txt");
            hst.save(file);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * Function Name: shouldStop.
     * @return if should stop showing on screen.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
