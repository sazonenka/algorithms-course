/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/13/2012
 *  Last updated:  9/13/2012
 *
 *  Represents a set of points in the unit square using a red-black BST.
 *  Uses brute-force implementation of range() and nearest().
 *
 *----------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points; // the red-black BST

    /**
     * Constructs an empty set of points.
     */
    public PointSET() {
        this.points = new TreeSet<Point2D>();
    }

    /**
     * Checks if the set is empty.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     */
    public int size() {
        return points.size();
    }

    /**
     * Adds the point p to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * Checks if the set contains the point p.
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /**
     * Locates all points in the set that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> range = new ArrayList<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                range.add(point);
            }
        }
        return range;
    }

    /**
     * Locates a nearest neighbor in the set to p; null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        Point2D minPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            double distance = p.distanceSquaredTo(point);
            if (Double.compare(distance, minDistance) < 0) {
                minPoint = point;
                minDistance = distance;
            }
        }
        return minPoint;
    }

    /**
     * Draws all of the points to standard draw.
     */
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

}
