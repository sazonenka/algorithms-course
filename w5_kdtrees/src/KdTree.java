/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/15/2012
 *  Last updated:  9/15/2012
 *
 *  Represents a set of points in the unit square using a 2d-tree.
 *  Supports efficient implementation of range search and
 *  nearest neighbor search.
 *
 *----------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private Node root; // the root node of the tree
    private int n;     // the size of the tree

    /**
     * Constructs an empty set of points.
     */
    public KdTree() {
        this.root = null;
        this.n = 0;
    }

    /**
     * Checks if the set is empty.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of points in the set.
     */
    public int size() {
        return n;
    }

    /**
     * Adds the point p to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D point, boolean vertical) {
        if (node == null) {
            n = n + 1;
            return new Node(point, vertical, null, null);
        }

        Point2D nodePoint = node.getPoint();
        if (nodePoint.equals(point)) {
            return node;
        }

        Comparator<Point2D> comparator = Point2D.Y_ORDER;
        if (vertical) {
            comparator = Point2D.X_ORDER;
        }

        int cmp = comparator.compare(point, nodePoint);
        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), point, !vertical));
        } else {
            node.setRight(insert(node.getRight(), point, !vertical));
        }
        return node;
    }

    /**
     * Checks if the set contains the point p.
     */
    public boolean contains(Point2D p) {
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D point) {
        if (node == null) {
            return false;
        }

        Point2D nodePoint = node.getPoint();
        if (nodePoint.equals(point)) {
            return true;
        }

        Comparator<Point2D> comparator = Point2D.Y_ORDER;
        if (node.isVertical()) {
            comparator = Point2D.X_ORDER;
        }

        int cmp = comparator.compare(point, nodePoint);
        if (cmp < 0) {
            return contains(node.getLeft(), point);
        } else {
            return contains(node.getRight(), point);
        }
    }

    /**
     * Locates all points in the set that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> range = new ArrayList<Point2D>();
        range(root, rect, range);
        return range;
    }

    private void range(Node node, RectHV rect, List<Point2D> range) {
        if (node == null) {
            return;
        }

        Point2D nodePoint = node.getPoint();
        if (rect.contains(nodePoint)) {
            range.add(nodePoint);
        }

        double pointCoord = nodePoint.y();
        double rectCoordMin = rect.ymin();
        double rectCoordMax = rect.ymax();
        if (node.isVertical()) {
            pointCoord = nodePoint.x();
            rectCoordMin = rect.xmin();
            rectCoordMax = rect.xmax();
        }

        if (rectCoordMin < pointCoord) {
            range(node.getLeft(), rect, range);
        }
        if (rectCoordMax >= pointCoord) {
            range(node.getRight(), rect, range);
        }
    }

    /**
     * Locates a nearest neighbor in the set to p; null if set is empty.
     */
    public Point2D nearest(Point2D p) {
        MinPQ<Point2D> queue = new MinPQ<Point2D>(distanceOrder(p));
        nearest(root, new RectHV(0.0, 0.0, 1.0, 1.0), p, queue);
        return queue.min();
    }

    private Comparator<Point2D> distanceOrder(final Point2D query) {
        return new Comparator<Point2D>() {
            @Override
            public int compare(Point2D p, Point2D q) {
                double dist1 = query.distanceSquaredTo(p);
                double dist2 = query.distanceSquaredTo(q);
                if      (dist1 < dist2) return -1;
                else if (dist1 > dist2) return +1;
                else                    return  0;
            }
        };
    }

    private void nearest(Node node, RectHV rect,
                         Point2D query, MinPQ<Point2D> queue) {
        if (node == null) {
            return;
        }

        Point2D point = node.getPoint();
        queue.insert(point);

        if (node.isVertical()) {
            Point2D bottom = new Point2D(point.x(), rect.ymin());
            Point2D top = new Point2D(point.x(), rect.ymax());

            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(),
                    top.x(), top.y());
            RectHV rightRect = new RectHV(bottom.x(), bottom.y(),
                    rect.xmax(), rect.ymax());

            if (query.x() < point.x()) {
                nearestForSubTrees(node.getLeft(), node.getRight(),
                        leftRect, rightRect, query, queue);
            } else {
                nearestForSubTrees(node.getRight(), node.getLeft(),
                        rightRect, leftRect, query, queue);
            }
        } else {
            Point2D left = new Point2D(rect.xmin(), point.y());
            Point2D right = new Point2D(rect.xmax(), point.y());

            RectHV bottomRect = new RectHV(rect.xmin(), rect.ymin(),
                    right.x(), right.y());
            RectHV topRect = new RectHV(left.x(), left.y(),
                    rect.xmax(), rect.ymax());

            if (query.y() < point.y()) {
                nearestForSubTrees(node.getLeft(), node.getRight(),
                        bottomRect, topRect, query, queue);
            } else {
                nearestForSubTrees(node.getRight(), node.getLeft(),
                        topRect, bottomRect, query, queue);
            }
        }
    }

    private void nearestForSubTrees(Node firstNode, Node secondNode,
                                    RectHV firstRect, RectHV secondRect,
                                    Point2D query, MinPQ<Point2D> queue) {
        nearest(firstNode, firstRect, query, queue);

        double distanceToChampion = queue.min().distanceSquaredTo(query);
        double distanceToSecondRect = secondRect.distanceSquaredTo(query);
        if (distanceToSecondRect < distanceToChampion) {
            nearest(secondNode, secondRect, query, queue);
        }
    }

    /**
     * Draws all of the points to standard draw.
     */
    public void draw() {
        draw(root, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private void draw(Node node, RectHV rect) {
        if (node == null) {
            return;
        }

        Point2D point = node.getPoint();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        point.draw();

        if (node.isVertical()) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();

            Point2D bottom = new Point2D(point.x(), rect.ymin());
            Point2D top = new Point2D(point.x(), rect.ymax());
            bottom.drawTo(top);

            draw(node.getLeft(), new RectHV(rect.xmin(), rect.ymin(),
                    top.x(), top.y()));
            draw(node.getRight(), new RectHV(bottom.x(), bottom.y(),
                    rect.xmax(), rect.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();

            Point2D left = new Point2D(rect.xmin(), point.y());
            Point2D right = new Point2D(rect.xmax(), point.y());
            left.drawTo(right);

            draw(node.getLeft(), new RectHV(rect.xmin(), rect.ymin(),
                    right.x(), right.y()));
            draw(node.getRight(), new RectHV(left.x(), left.y(),
                    rect.xmax(), rect.ymax()));
        }
    }

    /**
     * Node of a 2d-tree.
     */
    private static class Node {
        private Point2D point;      // the point
        private boolean vertical;   // if the point splits area
                                    //  with a vertical line?
        private Node left;          // the left/bottom subtree
        private Node right;         // the right/top subtree

        private Node(Point2D point, boolean vertical, Node left, Node right) {
            this.point = point;
            this.vertical = vertical;
            this.left = left;
            this.right = right;
        }

        /** Returns the point. */
        public Point2D getPoint() { return point; }
        /** Returns the vertical flag. */
        public boolean isVertical() { return vertical; }

        /** Returns the left/bottom subtree. */
        public Node getLeft() { return left; }
        /** Sets the left/bottom subtree. */
        public void setLeft(Node newLeft) { this.left = newLeft; }

        /** Returns the right/top subtree. */
        public Node getRight() { return right; }
        /** Sets the right/top subtree. */
        public void setRight(Node newRight) { this.right = newRight; }
    }

}
