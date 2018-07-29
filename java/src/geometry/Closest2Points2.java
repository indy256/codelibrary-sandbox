import java.util.*;

public class Closest2Points2 {

	private static class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double distance(Point b) {
			return Math.hypot(b.x - x, b.y - y);
		}
	}

	private static class CompareX implements Comparator<Point> {
		public int compare(Point a, Point b) // orders by X, breaking ties by Y
		{
			if (a.x < b.x) return -1;
			else if (a.x > b.x) return 1;
			else if (a.y < b.y) return -1;
			else if (a.y > b.y) return 1;
			else return 0;
		}
	}

	private static class CompareY implements Comparator<Point> {
		public int compare(Point a, Point b) // orders by Y, breaking ties by X
		{
			if (a.y < b.y) return -1;
			else if (a.y > b.y) return 1;
			else if (a.x < b.x) return -1;
			else if (a.x > b.x) return 1;
			else return 0;
		}
	}

	public static double closest(List<Point> S) {
		CompareX compareX = new CompareX();
		CompareY compareY = new CompareY();
		Point[] Sx = S.toArray(new Point[0]);  // Take a copy we will sort by X
		int N = Sx.length;
		SortedSet<Point> Sy = new TreeSet<Point>(compareY); // Active points
		int tail = 0;                          // points in Sx in the range [tail, i) are in Sy

		Arrays.sort(Sx, compareX);
		double h = Double.POSITIVE_INFINITY;

		for (int i = 0; i < N; i++) {
			// erase points whose X value is too small to even consider
			while (Sx[i].x - Sx[tail].x > h) {
				Sy.remove(Sx[tail]);
				tail++;
			}

			// Identify points in Sy within h vertically of Sx[i]
			SortedSet<Point> range = Sy.subSet(
					new Point(Double.NEGATIVE_INFINITY, Sx[i].y - h),
					new Point(Double.POSITIVE_INFINITY, Sx[i].y + h));
			for (Point p : range) {
				h = Math.min(h, Sx[i].distance(p));
			}

			Sy.add(Sx[i]); // Add latest point to active set
		}

		return h;
	}
}
