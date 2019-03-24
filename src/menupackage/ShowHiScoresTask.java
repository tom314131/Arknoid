package menupackage;

import gamepackage.AnimationRunner;
import interfaces.Animation;
import interfaces.Task;

/**
 * Class Name: ShowHiScoresTask.
 */
public class ShowHiScoresTask implements Task<Void> {

    //members
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Constructor.
     * @param runner - animation runner
     * @param highScoresAnimation - high score animation.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * Function Name: run.
     * @return high score animation running
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
