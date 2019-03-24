package interfaces;

import sprites.Ball;
import sprites.Block;

/**
 * Interface Name:HitListener.
 */
public interface HitListener {
    /**
     * Function Name: hitEvent.
     * Function Operation: This method is called whenever the beingHit object is hit.
     *      he hitter parameter is the Ball that's doing the hitting.
     * @param beingHit - the  block
     * @param hitter - the ball
     */
    void hitEvent(Block beingHit, Ball hitter);
}
