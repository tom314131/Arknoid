package menupackage;

import gamepackage.AnimationRunner;
import interfaces.Animation;
import interfaces.Task;

/**
 * Class Name: EndGameTask.
 */
public class EndGameTask implements Task<Void> {

    //members
    private AnimationRunner runner;
    private Animation endGameAnimation;

    /**
     * Constructor.
     * @param runner - the animation runner
     * @param endGameAnimation - the end game animation
     */
    public EndGameTask(AnimationRunner runner, Animation endGameAnimation) {
        this.runner = runner;
        this.endGameAnimation = endGameAnimation;
    }

    /**
     * Function Name: run.
     * Function Operation: run the end game animation.
     * @return nothing
     */
    public Void run() {
        this.runner.run(this.endGameAnimation);
        return null;
    }
}
