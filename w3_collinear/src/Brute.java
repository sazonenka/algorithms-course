import java.util.Arrays;

public class Brute {

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
        for (int a = 0; a < points.length; a++) {
            Point aPoint = points[a];
            for (int b = a + 1; b < points.length; b++) {
                Point bPoint = points[b];
                double abSlope = aPoint.slopeTo(bPoint);

                for (int c = b + 1; c < points.length; c++) {
                    Point cPoint = points[c];
                    double acSlope = aPoint.slopeTo(cPoint);

                    if (Double.compare(abSlope, acSlope) == 0) {
                        for (int d = c + 1; d < points.length; d++) {
                            Point dPoint = points[d];
                            double adSlope = aPoint.slopeTo(dPoint);

                            if (Double.compare(abSlope, adSlope) == 0) {
                                drawSegment(aPoint, bPoint, cPoint, dPoint);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void drawSegment(Point aPoint,
                                    Point bPoint,
                                    Point cPoint,
                                    Point dPoint) {
        Point[] collinear = { aPoint, bPoint, cPoint, dPoint };
        Arrays.sort(collinear);

        collinear[0].drawTo(collinear[3]);
    }

}
