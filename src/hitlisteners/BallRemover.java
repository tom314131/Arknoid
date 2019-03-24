package hitlisteners;

import sprites.Ball;
import sprites.Block;
import interfaces.HitListener;
import gamepackage.Counter;
import gamepackage.GameLevel;

/**
 * Class Name: BallRemover.
 */
public class BallRemover implements HitListener {

    //members
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Function Name: BallRemover.
     * Function Operation: Constructor
     * @param gameLevel - the game level
     * @param remainingBalls - number of balls we have
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Function Name: getRemainingBalls.
     * @return the remaining balls
     */
    public Counter getRemainingBalls() {
        return this.remainingBalls;
    }

    /**
     * Function Name: setRemainingBalls.
     * Function Operation: set the remaining balls in the game.
     * @param counter - new remaining balls
     */
    public void setRemainingBalls(Counter counter) {
        this.remainingBalls = counter;
    }

    /**
     * Function Name: hitEvent.
     * Function Operation: if the ball was hit it is removed from the game
     *  and so is its' listener.
     * @param beingHit - the block
     * @param hitter - the ball
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeHitListener(this);
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
    }
}
