package geomitryprimitives;

/**
 * Class Name: Velocity.
 */
public class Velocity {
    private double dx;
    private double dy;
    private double speed;

    /**
     * Function Name: Velocity.
     * constructors
     * @param dx - velocity on x dimension
     * @param dy - velocity on y dimension
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     *  Function Name: applyToPoint.
     * @param p - the current point
     * @return Take a point with position (x,y) and return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        Point newPoint = new Point(this.dx + p.getX(), this.dy + p.getY());
        return newPoint;
    }

    /**
     * Function Name: fromAngleAndSpeed.
     * @param angle - the angle the ball has to move
     * @param speed - the speed the ball has to move
     * @return the velocity in form of dx and dy
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = (-1) * Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Function Name: setDX.
     * @param xSpeed - the motion on dimension x
     */
    public void setDX(double xSpeed) {
        this.dx = xSpeed;
    }
    /**
     * Function Name: setDY.
     * @param ySpeed - the motion on dimension y
     */
    public void setDY(double ySpeed) {
        this.dy = ySpeed;
    }

    /**
     * Function Name: getDX.
     * @return the speed of the ball on dimension x
     */
    public double getDX() {
        return this.dx;
    }
    /**
     * Function Name: getDY.
     * @return the speed of the ball on dimension y
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * Function Name: getSpeed.
     * @return the speed of the ball.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * Function Name: setSpeed.
     * @param speedInseret - the speed
     */
    public void setSpeed(double speedInseret) {
        this.speed = speedInseret;
    }
}

