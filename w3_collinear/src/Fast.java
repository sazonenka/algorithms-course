import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fast {

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String fileName = args[0];

        Point[] points = extractPoints(fileName);
        drawPoints(points);

        findCollinearPoints(points);

        StdDraw.show(0);
    }

    private static Point[] extractPoints(String fileName) {
        In in = new In(fileName);

        int n = in.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            Point point = new Point(x, y);
            points[i] = point;

            point.draw();
        }

        return points;
    }

    private static void drawPoints(Point[] points) {
        for (Point point : points) {
            point.draw();
        }
    }

    private static void findCollinearPoints(Point[] points) {
        int n = points.length;
        for (int a = 0; a < n - 3; a++) {
            Point aPoint = points[a];

            List<Point> candidates = new ArrayList<Point>();
            for (int b = a + 1; b < n; b++) {
                candidates.add(points[b]);
            }

            Collections.sort(candidates, aPoint.SLOPE_ORDER);

            // Find sequence of collinear points
        }
    }

    private static void drawSegment(Point aPoint,
                                    Point bPoint,
                                    Point cPoint,
                                    Point dPoint) {
        Point[] collinear = { aPoint, bPoint, cPoint, dPoint };
        Arrays.sort(collinear);

        StdOut.printf("%s -> %s -> %s -> %s\n", collinear[0],
                collinear[1],
                collinear[2],
                collinear[3]);
        collinear[0].drawTo(collinear[3]);
    }

}
