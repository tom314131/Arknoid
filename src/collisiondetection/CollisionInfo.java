package collisiondetection;

import interfaces.Collidable;
import geomitryprimitives.Point;
/**
 * Class Name: CollisionInfo.
 */
public class CollisionInfo {
    //members
    private Point collPoint;
    private Collidable collidable;

    /**
     * Function Name: CollisionInfo.
     * constructors
     * @param point - the collision point
     * @param collidable - the collidable
     */
    public CollisionInfo(Point point, Collidable collidable) {
        this.collPoint = point;
        this.collidable = collidable;
    }

    /**
     * Function Name: collisionPoint.
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collPoint;
    }

    /**
     * Function Name: collisionObject.
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}

