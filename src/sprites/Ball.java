package sprites;

import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import biuoop.DrawSurface;
import interfaces.Collidable;
import collisiondetection.CollisionInfo;
import collisiondetection.GameEnvironment;
import gamepackage.GameLevel;
import geomitryprimitives.Point;
import geomitryprimitives.Line;
import geomitryprimitives.Rectangle;
import geomitryprimitives.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: Ball.
 */
public class Ball implements Sprite, HitNotifier {
    private GameEnvironment gameEnvironment;
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private Point topLeftCorner;
    private Point bottomRightCorner;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();


    /**
     * Function Name: Ball.
     * constructors
     * @param center - center point of the ball
     * @param r - radius of the ball
     * @param color - the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Function Name: returnCenter.
     * @return the center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Function Name: setCenter.
     * @param ballCenter - the center of the ball
     */
    public void setCenter(Point ballCenter) {
        this.center = ballCenter;
    }
    /**
     * Function Name: Ball.
     * @param x - the x value of the center point of the ball
     * @param y - the y value of the center point of the ball
     * @param r - radius of the ball
     * @param color - the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }
    /**
     * Function Name: getX.
     * @return the x value of the center point
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * Function Name: getY.
     * @return the y value of the center point
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Function Name: getSize.
     * @return the radius/size of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Function Name: getColor.
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Function Name: drawOn.
     * Function Operation: draw the ball on the given DrawSurface
     * @param surface - the surface that we draw the ball
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.r);
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.r);
    }

    /**
     * Function Name: timePassed.
     * Function Operation: call moveOneStep method.
     * @param dt - 1/fps
     */
    public void timePassed(double dt) {
        this.moveOneStep();
    }

    /**
     * Function Name: setVelocity.
     * Function Operation: set the velocity of the ball
     * @param vel - the velocity of the ball
     */
    public void setVelocity(Velocity vel) {
        this.v = vel;
    }

    /**
     * Function Name: setVelocity.
     * Function Operation: set the velocity of the ball
     * @param dx - the speed of the ball on the x dimension
     * @param dy - the speed of the ball on the y dimension
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);

    }

    /**
     * Function Name: getVelocity.
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Function Name: moveOneStep.
     * Function Operation: move the ball by the velocity
     */
    public void moveOneStep() {
        if (this.getVelocity() == null) {
            return;
        }
        Point currentPoint = new Point(this.center.getX(), this.center.getY());
        Point endPoint = new Point(this.center.getX() + this.v.getDX(),
                this.center.getY() + this.v.getDY());
        Line trajectory = new Line(currentPoint, endPoint);
        CollisionInfo ci = this.gameEnvironment.getClosestCollision(trajectory);
        if (ci != null) {
            Point cPoint = ci.collisionPoint();
            Collidable c = ci.collisionObject();
            Rectangle rect = c.getCollisionRectangle();
            double x = cPoint.getX();
            double y = cPoint.getY();
            if (cPoint.getX() == rect.getUpperLeft().getX()) {
                x = cPoint.getX() - r - 0.001;
            } else if (cPoint.getX() == rect.getUpperRight().getX()) {
                x = cPoint.getX() + r + 0.001;
            }
            if (cPoint.getY() == rect.getUpperLeft().getY()) {
                y = cPoint.getY() - r - 0.001;
            } else if (cPoint.getY() == rect.getBottomLeft().getY()) {
                y = cPoint.getY() + r + 0.001;
            }
            this.setVelocity(c.hit(this, cPoint, this.v));
            this.center = new Point(x, y);
        } else {
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * Function Name: setLimit.
     * Function Operation: setting the corners of the frame
     * @param topLeft - the top left corner that the ball can move
     * @param bottomRight - the bottom right corner that the ball can move
     */
    public void setLimit(Point topLeft, Point bottomRight) {
        this.topLeftCorner = topLeft;
        this.bottomRightCorner = bottomRight;
    }
    /**
     * Function Name: setGameEnvironment.
     * Function Operation: setting the gameEnvironment
     * @param gameEnv - the game environment of the game
     */
    public void setGameEnvironment(GameEnvironment gameEnv) {
        this.gameEnvironment = gameEnv;
    }

    /**
     * Function Name: addToGame.
     * Function Operation: add the ball to the gameLevel
     * @param gameLevel - the gameLevel
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * Function Name: removeFromGame.
     * Function Operation: remove the ball from the gameLevel
     * @param gameLevel - the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) { gameLevel.removeSprite(this); }

    /**
     * Function Name: addHitListener.
     * Function Operation: add hit listener
     * @param hl - the listener.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Function Name: removeHitListener.
     * Function Operation: remove hit listener
     * @param hl - the listener.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

}

