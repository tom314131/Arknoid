package gamepackage;

import indicators.LivesIndicator;
import interfaces.LevelInformation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import hitlisteners.ScoreTrackingListener;
import menupackage.HighScoresTable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class Name: GameFlow.
 */
public class GameFlow {

    //members
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private LivesIndicator lives;
    private ScoreTrackingListener scoreTrackingListener;
    private File file;
    private double dt;

    /**
     * Function Name: GameFlow.
     * Function Operation: Constructor.
     * @param ar  - the animation runner of the game
     * @param ks  - the keyboard sensor
     * @param gui - the gui
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.file = new File("highscores.txt");
    }

    /**
     * Function Name: runLevels.
     * Function Operation: runs the levels of the game.
     * @param levels - the levels of the game
     * @throws IOException if cannot read file.
     */
    public void runLevels(List<LevelInformation> levels) throws IOException {
        HighScoresTable scores = new HighScoresTable(10);
        if (file.exists()) {
            try {
                scores.load(file);

            } catch (IOException ex) {
                ex.getMessage();
            }
        } else {
            try {
                scores.save(file);
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        this.scoreTrackingListener = new ScoreTrackingListener(new Counter(0));
        this.lives = new LivesIndicator(new Counter(7));
        boolean last = false;
        for (int i = 0; i < levels.size(); i++) {
            LevelInformation levelInfo = levels.get(i);
            if (i == levels.size() - 1) {
                last = true;
            }
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.gui, this.scoreTrackingListener, this.lives, last);
            level.setDt(this.dt);
            level.setScore(scores);

            level.initialize();

            while (level.returnLives() > 0 && level.returnBlocks() > 0) {
                level.playOneTurn();
            }
        }

    }
    /**
     * Function Name:setDt.
     * Function Operation: set the dt
     * @param dts - 1/fps
     */
    public void setDt(double dts) {
        this.dt = dts;
    }


}
