package geomitryprimitives;

import java.util.ArrayList;

/**
 * Class Name: Rectangle.
 */
public class Rectangle {
    private Point upLeftPoint;
    private double widthRec;
    private double heightRec;

    /**
     * Function Name: Rectangle.
     * Function Operation: Create a new rectangle with location and width/height.
     * @param upperLeft - upper left point of rectangle
     * @param width - width of rectangle
     * @param height - height og rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upLeftPoint = upperLeft;
        this.widthRec = width;
        this.heightRec = height;
    }

    /**
     * Function Name: intersectionPoints.
     * @param line - the line we check intersection
     * @return a (possibly empty) List of intersection points
     * with the specified line.
     */
    public java.util.List intersectionPoints(Line line) {
        ArrayList<Point> list = new ArrayList<Point>();
        Line topRec = new Line(this.upLeftPoint.getX(), this.upLeftPoint.getY(),
                this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY());
        Line bottomRec = new Line(this.upLeftPoint.getX(), this.upLeftPoint.getY() + heightRec,
                this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY() + heightRec);
        Line rightRec = new Line(this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY(),
                this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY() + heightRec);
        Line leftRec = new Line(this.upLeftPoint.getX(), this.upLeftPoint.getY(),
                this.upLeftPoint.getX(), this.upLeftPoint.getY() + heightRec);
        Point point1 = line.intersectionWith(topRec);
        Point point2 = line.intersectionWith(bottomRec);
        Point point3 = line.intersectionWith(rightRec);
        Point point4 = line.intersectionWith(leftRec);
        if (point1 != null) {
            list.add(point1);
        }
        if (point2 != null) {
            list.add(point2);
        }
        if (point3 != null) {
            list.add(point3);
        }
        if (point4 != null) {
            list.add(point4);
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    /**
     * Function Name:getWidth.
     *@return Return the width and height of the rectangle.
     */
    public double getWidth() {
        return this.widthRec;
    }

    /**
     * Function Name:getHeight.
     *@return Return the height and height of the rectangle.
     */
    public double getHeight() {
        return this.heightRec;
    }

    /**
     * Function Name: getUpperLeft.
     * @return the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upLeftPoint;
    }
    /**
     * Function Name: getUpperRight.
     * @return the upper right point of the rectangle.
     */
    public Point getUpperRight() {
        return new Point(this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY());
    }
    /**
     * Function Name: getBottomLeft.
     * @return the bottom left point of the rectangle.
     */
    public Point getBottomLeft() {
        return new Point(this.upLeftPoint.getX(), this.upLeftPoint.getY() + heightRec);
    }

    /**
     * Function Name: getBottomRight.
     * @return the bottom right point of the rectangle.
     */
    public Point getBottomRight() {
        return new Point(this.upLeftPoint.getX() + widthRec, this.upLeftPoint.getY() + heightRec);
    }

    /**
     * Function Name: setUpLeftPoint.
     * @param point - the new left point of the rectangle
     */
    public void setUpLeftPoint(Point point) {
        this.upLeftPoint = point;
    }

    /**
     * Function Name: setWidth.
     * Function Operation: set the width of the rectangle.
     * @param width - width of the rectangle
     */
    public void setWidth(int width) {
        this.widthRec = width;
    }
    /**
     * Function Name: setHeight.
     * Function Operation: set the width of the rectangle.
     * @param height - height of the rectangle
     */
    public void setHeight(double height) {
        this.heightRec = heightRec;
    }
}

