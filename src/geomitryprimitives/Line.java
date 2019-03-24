package geomitryprimitives;
import java.util.List;

/**
 * Class Name: Line.
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * constructors.
     * @param start - getting the start point of the line
     * @param end - getting the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    /**
     * @param x1 - getting the x value of the start point
     * @param y1 - getting the y value of the start point
     * @param x2 - getting the x value of the end point
     * @param y2 - getting the y value of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Function Name: length.
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Function Name: middle.
     * @return the middle point of the line
     */
    public Point middle() {
        Point mid = new Point((this.start.getX() + this.end.getX()) / 2,
                (this.start.getY() + this.end.getY()) / 2);
        return mid;
    }

    /**
     * Function Name: start.
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Function Name: end.
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Function Name: isIntersecting.
     * @param other - the other line
     * @return true if the two lines are intersecting, false otherwise
     */
    public boolean isIntersecting(Line other) {
        Point point = this.intersectionWith(other);
        if (point != null) {
            return false;
        }
        return true;
    }

    /**
     * Function Name: intersectionWith.
     * @param other - the other line
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        //checking if the length of one of the lines is 0
        if (this.length() == 0 || other.length() == 0) {
            return null;
        }
        if (!this.isHorizontal() && !this.isVertical() && !other.isHorizontal() && !other.isVertical()) {
            double m1 = this.incline();
            double m2 = other.incline();
            if (m1 == m2) {
                //if the incline of the lines is the same we return null
                return null;
            }
            double n1 = this.start.getY() - m1 * this.start.getX();
            double n2 = other.start.getY() - m2 * other.start.getX();
            double xPoint = (n2 - n1) / (m1 - m2);
            Point checkPoint = new Point(xPoint, m1 * xPoint + n1);
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (this.isHorizontal() && other.isVertical()) {
            Point checkPoint = new Point(other.start.getX(), this.start.getY());
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (this.isVertical() && other.isHorizontal()) {
            Point checkPoint = new Point(this.start.getX(), other.start.getY());
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (this.isHorizontal() && !other.isHorizontal() && !other.isVertical()) {
            double m = other.incline();
            double n = other.start.getY() - m * other.start.getX();
            double xPoint = (this.start.getY() - n) / m;
            Point checkPoint = new Point(xPoint, this.start.getY());
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (other.isHorizontal() && !this.isHorizontal() && !this.isVertical()) {
            double m = this.incline();
            double n = this.start.getY() - m * this.start.getX();
            double xPoint = (other.start.getY() - n) / m;
            Point checkPoint = new Point(xPoint, other.start.getY());
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (this.isVertical() && !other.isVertical() && !other.isHorizontal()) {
            double m = other.incline();
            double n = other.start.getY() - m * other.start.getX();
            double yPoint = m * this.start.getX() + n;
            Point checkPoint = new Point(this.start.getX(), yPoint);
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        if (other.isVertical() && !this.isVertical() && !this.isHorizontal()) {
            double m = this.incline();
            double n = this.start.getY() - m * this.start.getX();
            double yPoint = m * other.start.getX() + n;
            Point checkPoint = new Point(other.start.getX(), yPoint);
            if (this.pointOnLine(checkPoint) && other.pointOnLine(checkPoint)) {
                return checkPoint;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Function Name: equals.
     * @param other - the other line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (this.start.distance(this.end) == other.start().distance((other.end))) {
            return true;
        }
        return false;
    }

    /**
     * Function Name: isHorizontal.
     * @return true if line is horizontal and false otherwise
     */
    public boolean isHorizontal() {
        if (this.start.getY() == this.end.getY()) {
            return true;
        }
        return false;
    }
    /**
     * Function Name: isVertical.
     * @return true if line is vertical and false otherwise
     */
    public boolean isVertical() {
        if (this.start.getX() == this.end.getX()) {
            return true;
        }
        return false;
    }

    /**
     * Function Name: closestIntersectionToStartOfLine.
     * Function Operation - If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     * @param rect - the rectangle we collide
     * @return the closest intersection point to the start of the line if there is,
     * otherwise null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line line = new Line(this.start, this.end);
        List list = rect.intersectionPoints(line);
        if (list == null) {
            return null;
        } else {
            double minLine = this.start.distance((Point) list.get(0));
            Point minPoint = (Point) list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (this.start.distance((Point) list.get(i)) < minLine) {
                    minLine = this.start.distance((Point) list.get(i));
                    minPoint = (Point) list.get(i);
                }
            }
            return minPoint;
        }
    }

    /**
     * Function Name: pointOnLine.
     * @param point - point we check
     * @return true if point on line, and false if it is not
     */
    public boolean pointOnLine(Point point) {
        if (this.isHorizontal()) {
            if (point.getX() >= Math.min(this.start.getX(), this.end.getX())
                    && point.getX() <= Math.max(this.start.getX(), this.end.getX())
                    && point.getY() ==  this.start.getY()) {
                return true;
            }
        }
        if (this.isVertical()) {
            if (point.getY() >= Math.min(this.start.getY(), this.end.getY())
                    && point.getY() <= Math.max(this.start.getY(), this.end.getY())
                    && point.getX() == this.start.getX()) {
                return true;
            }
        }
        if (!this.isVertical() && !this.isHorizontal()) {
            double m = this.incline();
            double n = this.end.getY() - m * this.end.getX();
            double y0 = m * point.getX() + n;
            if (y0 >= Math.min(this.start.getY(), this.end.getY())
                    && y0 <= Math.max(this.start.getY(), this.end.getY())) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Function Name: incline.
     * @return the incline of the line
     */
    public double incline() {
        if (this.isVertical()) {
            return 0;
        } else {
            return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }
    }
}

