package interfaces;

import sprites.Ball;
import geomitryprimitives.Rectangle;
import geomitryprimitives.Velocity;
import geomitryprimitives.Point;
/**
 * Interface Name:Collidable.
 */
public interface Collidable {
    /**
     * Funtion Name: getCollisionRectangle.
     * Function Operation: Return the "collision shape" of the object.
     * @return the rectangle that collides
     */

    Rectangle getCollisionRectangle();

    /**
     * Function Name: hit.
     * Function Operation:Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * @param ball - the ball.
     * @param collisionPoint - the collision point with the collidable
     * @param currentVelocity - the velocity of the ball
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity);

}

