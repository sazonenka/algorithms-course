/*************************************************************************
 * Name: Aliaksandr Sazonenka
 * Email: aliaksandr.sazonenka@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point q, Point r) {
            return Double.compare(slopeTo(q), slopeTo(r));
        }
    };

    private final int x; // x coordinate
    private final int y; // y coordinate

    /** Creates the point (x, y). */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Plots this point to standard drawing. */
    public void draw() {
        StdDraw.point(x, y);
    }

    /** Draws line between this point and that point to standard drawing. */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /** Slope between this point and that point. */
    public double slopeTo(Point that) {
        int dx = that.x - this.x;
        int dy = that.y - this.y;

        if (dx == 0 && dy == 0) return Double.NEGATIVE_INFINITY;
        if (dx == 0)            return Double.POSITIVE_INFINITY;
        if (dy == 0)            return 0.0;

        return 1.0 * dy / dx;
    }

    /**
     * Is this point lexicographically smaller than that one?
     * Comparing y-coordinates and breaking ties by x-coordinates.
     */
    public int compareTo(Point that) {
        if (this.y == that.y) return this.x - that.x;
        else                  return this.y - that.y;
    }

    /** Returns string representation of this point. */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
