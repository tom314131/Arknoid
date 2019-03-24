package sprites;

import interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Collidable;
import gamepackage.GameLevel;
import geomitryprimitives.Point;
import geomitryprimitives.Rectangle;
import geomitryprimitives.Velocity;
import java.util.List;

import java.awt.Color;

/**
 * Class Name: Paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle pad;
    private double guiWidth;
    private double sideFrameBlockWidth;
    private double speed;
    private List<Ball> balls;

    /**
     * Function Name: Paddle.
     * constructor
     * @param pad      - the paddle
     * @param keyboard - keyboard sensor
     */
    public Paddle(Rectangle pad, KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.pad = pad;
    }

    /**
     * Function Name: moveLeft.
     * Function Operation: move the paddle to the left.
     */
    public void moveLeft() {
        if (this.pad.getUpperLeft().getX() > this.sideFrameBlockWidth) {
            this.pad.setUpLeftPoint(new Point(this.pad.getUpperLeft().getX() - this.speed,
                    this.pad.getUpperLeft().getY()));
            for (int i = 0; i < balls.size(); i++) {
                checkBallInPaddle(this.balls.get(i), true);
            }
        }
        if (this.pad.getUpperLeft().getX() <= this.sideFrameBlockWidth) {
            this.pad.setUpLeftPoint(new Point(this.sideFrameBlockWidth,
                    this.pad.getUpperLeft().getY()));
            for (int i = 0; i < balls.size(); i++) {
                checkBallInPaddle(this.balls.get(i), true);
            }
        }
    }

    /**
     * Function Name: moveRight.
     * Function Operation: move the paddle to the right.
     */
    public void moveRight() {
        if (this.pad.getUpperRight().getX() + this.speed < this.guiWidth - this.sideFrameBlockWidth) {
            this.pad.setUpLeftPoint(new Point(this.pad.getUpperLeft().getX() + this.speed,
                    this.pad.getUpperLeft().getY()));
            for (int i = 0; i < balls.size(); i++) {
                checkBallInPaddle(this.balls.get(i), false);
            }
        }
        if (this.pad.getUpperRight().getX() + this.speed >= this.guiWidth - this.sideFrameBlockWidth) {
            this.pad.setUpLeftPoint(new Point(this.guiWidth - this.sideFrameBlockWidth - this.pad.getWidth(),
                    this.pad.getUpperLeft().getY()));
            for (int i = 0; i < balls.size(); i++) {
                checkBallInPaddle(this.balls.get(i), false);
            }
        }
    }

    /**
     * Function Name: timePassed.
     * Function Operation: check if the paddle should move
     *      if it does it activate the needed function
     * @param dt - 1/fps
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        } else if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    /**
     * Function Name: drawOn.
     * Function Operation: draw the paddle
     *
     * @param d - the surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.YELLOW.darker());
        d.fillRectangle((int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getUpperLeft().getY(),
                (int) this.pad.getBottomRight().getX() - (int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getBottomRight().getY() - (int) this.pad.getUpperLeft().getY());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getUpperLeft().getY(),
                (int) this.pad.getBottomRight().getX() - (int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getBottomRight().getY() - (int) this.pad.getUpperLeft().getY());
    }

    /**
     * Function Name: getCollisionRectangle.
     *
     * @return the paddle as rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.pad;
    }

    /**
     * Function Name: hit.
     *  @param ball - the ball
     * @param collisionPoint  - the collision point with the paddle
     * @param currentVelocity - the velocity of the ball
     * @return the new velocity of the ball
     */
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {
        Velocity velocity = null;
        Rectangle rect = this.getCollisionRectangle();
        double width = rect.getWidth();
        double devideWidth = width / 5;
        double part1 = this.pad.getUpperLeft().getX() + devideWidth;
        double part2 = part1 + devideWidth;
        double part3 = part2 + devideWidth;
        double part4 = part3 + devideWidth;
        double part5 = part4 + devideWidth;
        if (collisionPoint.getY() == this.pad.getUpperLeft().getY()) {
            if (collisionPoint.getX() >= this.pad.getUpperLeft().getX() && collisionPoint.getX() < part1) {
                velocity = Velocity.fromAngleAndSpeed(-60, currentVelocity.getSpeed());
            }
            if (collisionPoint.getX() >= part1 && collisionPoint.getX() < part2) {
                velocity = Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
            }
            if (collisionPoint.getX() >= part2 && collisionPoint.getX() < part3) {
                velocity = new Velocity(0, (-1) * currentVelocity.getSpeed());
            }
            if (collisionPoint.getX() >= part3 && collisionPoint.getX() < part4) {
                velocity = Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            }
            if (collisionPoint.getX() >= part4 && collisionPoint.getX() < part5) {
                velocity = Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            }
            return velocity;
        }
        if (collisionPoint.getX() == this.pad.getUpperLeft().getX()) {
            velocity = Velocity.fromAngleAndSpeed(-60, currentVelocity.getSpeed());
        }
        if (collisionPoint.getX() == this.pad.getUpperLeft().getX() + width) {
            velocity = Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
        }
        return velocity;
    }

    /**
     * Function Name: addToGame.
     * Function Operation: add paddle to the game
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Function Name: removeFromGame.
     * Function Operation: remove paddle from the game
     * @param g - the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    /**
     * Function Name: getGuiWidth.
     * Function Operation: get the width of the gui
     *
     * @param widthOfGui - the width of the gui
     */
    public void getGuiWidth(double widthOfGui) {
        this.guiWidth = widthOfGui;
    }

    /**
     * Function Name: setBalls.
     * Function Operation: add balls to the paddle as members
     * @param b - list of balls
     */
    public void setBalls(List<Ball> b) {
        this.balls = b;
    }

    /**
     * Function Name: checkBallInPaddle.
     * @param ball - the ball
     * @param bool - indictation to see where is the ball
     */
    public void checkBallInPaddle(Ball ball, boolean bool) {
        double startPadX = this.pad.getUpperLeft().getX();
        double endPadX = this.pad.getUpperRight().getX();
        if (ball.getX() >= startPadX && ball.getX() <= endPadX && ball.getY() >= this.pad.getUpperLeft().getY()) {
            double x, y;
            if (bool) {
                x = startPadX - ball.getSize();
                y = ball.getY();

                if (x - ball.getSize() < this.sideFrameBlockWidth) {
                    x = this.sideFrameBlockWidth + ball.getSize();
                    y = this.pad.getUpperLeft().getY() - ball.getSize();
                }
                ball.setCenter(new Point(x, y));
            } else {
                x = endPadX + ball.getSize();
                y = ball.getY();
                if (x + ball.getSize() > this.guiWidth - this.sideFrameBlockWidth) {
                    x = this.guiWidth - this.sideFrameBlockWidth - ball.getSize();
                    y = this.pad.getUpperLeft().getY() - ball.getSize();
                }
                ball.setCenter(new Point(x, y));
            }
        }
    }

    /**
     * Function Name: setSideFrameBlockWidth.
     * @param width - width of block of frame
     */
    public void setSideFrameBlockWidth(double width) {
        this.sideFrameBlockWidth = width;
    }

    /**
     * Function Name: setSpeed.
     * @param sp - the speed of the paddle
     */
    public void setSpeed(double sp) {
        this.speed = sp;
    }

    /**
     * Function Name: leftUpPoint.
     * @return the left up point of the paddle
     */
    public Point leftUpPoint() {
        return this.pad.getUpperLeft();
    }
}
