package collisiondetection;

import java.util.ArrayList;
import java.util.List;

import interfaces.Collidable;
import geomitryprimitives.Point;
import geomitryprimitives.Rectangle;
import geomitryprimitives.Line;

/**
 * Class Name: GameEnvironment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Function Name: GameEnvironment.
     * constructors
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Function Name: addCollidable.
     * Function Operation: add the given collidable to the environment.
     * @param c - the collidable that we add to the game
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Function Name: removeCollidable.
     * Function Operation: remove the given collidable to the environment.
     * @param c - the collidable that we remove from the game
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Function Name: getClosestCollision.
     * @param trajectory - the trajectory of the ball
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        if (this.collidables.size() == 0) {
            return null;
        } else if (this.collidables.size() == 1) {
            Collidable collidable = (Collidable) this.collidables.get(0);
            Rectangle rectangle = collidable.getCollisionRectangle();
            Point point = trajectory.closestIntersectionToStartOfLine(rectangle);
            if (point == null) {
                return null;
            } else {
                return new CollisionInfo(point, collidable);
            }
        } else {
            Collidable minColidable = null;
            Point minPoint = null;
            for (int i = 0; i < collidables.size(); i++) {
                Collidable collidable = (Collidable) this.collidables.get(i);
                Rectangle rectangle = collidable.getCollisionRectangle();
                Point point = trajectory.closestIntersectionToStartOfLine(rectangle);
                if (minPoint == null && point != null) {
                    minColidable = collidable;
                    minPoint = point;

                } else if  (point != null && minPoint != null) {
                    Point startPoint = new Point(trajectory.start().getX(), trajectory.start().getY());
                    double mindis = minPoint.distance(startPoint);
                    double checkdis = point.distance(startPoint);
                    if (mindis > checkdis) {
                        minColidable = collidable;
                        minPoint = point;
                        mindis = checkdis;
                    }
                }
            }
            if (minPoint == null) {
                return null;
            } else {
                return new CollisionInfo(minPoint, minColidable);
            }
        }
    }
}

