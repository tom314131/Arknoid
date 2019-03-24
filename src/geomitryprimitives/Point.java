package geomitryprimitives;

/**
 * Class Name: Point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructors.
     * @param x - getting the x value of the point
     * @param y - getting the y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Function Name: distance.
     * @param other - other point
     * @return return the distance of this point to the other point
     */
    public double distance(Point other) {
        double dis = Math.sqrt((this.x - other.getX()) * (this.x - other.getX())
                + (this.y - other.getY()) * (this.y - other.getY()));
        return dis;
    }

    /**
     * Function Name: equals.
     * @param other - other point
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if ((this.x == other.getX()) && (this.y == other.getY())) {
            return true;
        }
        return false;
    }

    /**
     * Function Name: getX.
     * @return the x value of this point
     */
    public double getX() {
        return this.x;
    }
    /**
     * Function Name: getY.
     * @return the y value of this point
     */
    public double getY() {
        return this.y;
    }
    /**
     * Function Name: setX.
     * Function operation: set the x value of the point
     * @param xValue - the x that want to enter to the x value of the point
     */
    public void setX(double xValue) {
        this.x = xValue;
    }
    /**
     * Function Name: setY.
     * Function operation: set the y value of the point
     * @param yValue - the y that want to enter to the y value of the point
     */
    public void setY(double yValue) {
        this.y = yValue;
    }
}

