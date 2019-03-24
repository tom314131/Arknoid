package hitlisteners;

import sprites.Ball;
import sprites.Block;
import interfaces.HitListener;
import gamepackage.Counter;

/**
 * Class Name: ScoreTrackingListener.
 */
public class ScoreTrackingListener implements HitListener {
    //members
    private Counter currentScore;
    private Counter remainingBlocks;

    /**
     * Function Name: ScoreTrackingListener.
     * Function Operation: Constructor
     * @param scoreCounter - the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Function Name: hitEvent.
     * Function Operation: if a hit was made we update the score
     * @param beingHit - the block
     * @param hitter   - the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(15);
        }
        if (beingHit.getHitPoints() == 1 || beingHit.getHitPoints() == 2) {
            this.currentScore.increase(5);
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.currentScore.increase(100);
        }

    }

    /**
     * Function Name: getCurrentScore.
     * @return the current score.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    /**
     * Function Name: setRemainingBlocks.
     * Function Operation: set the remaining blocks in the game.
     * @param remainBlocks - new remaining balls
     */
    public void setRemainingBlocks(Counter remainBlocks) {
        this.remainingBlocks = remainBlocks;
    }

}
