package hitlisteners;

import sprites.Ball;
import sprites.Block;
import interfaces.HitListener;
import gamepackage.Counter;
import gamepackage.GameLevel;

/**
 * Class Name: BlockRemover.
 */
public class BlockRemover implements HitListener {

   //members
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Function Name: BlockRemover.
     * Function Operation: Constructor
     * @param gameLevel - the game level
     * @param remainingBlocks - number of blocks we have
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }
    /**
     * Function Name: setRemainingBlocks.
     * Function Operation: set the remaining blocks in the game.
     * @param counter - new remaining balls
     */
    public void setRemainingBlocks(Counter counter) {
        this.remainingBlocks = counter;
    }
    /**
     * Function Name: getRemainingBlocks.
     * @return the remaining blocks
     */
    public Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }


    /**
     * Function Name: hitEvent.
     * Function Operation: if the ball was hit it is removed from the game
     *  and so is its' listener.
     * @param beingHit - the block
     * @param hitter - the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.gameLevel);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}
