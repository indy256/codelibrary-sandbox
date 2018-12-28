package tmp;
import java.util.Random;

public class NearestNeighbours {

	public static long[] ann(int[] x, int[] y) {
		int n = x.length;
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++)
			points[i] = new Point(i, x[i], y[i]);
		rec(points, 0, n);

		long[] res = new long[n];
		for (Point p : points)
			res[p.id] = p.d;
		return res;
	}

	static void rec(Point[] points, int from, int to) {
		int size = to - from;
		if (size <= 1)
			return;
		if (size == 2) {
			long d = dist(points[from], points[to]);
			points[from].d = Math.min(points[from].d, d);
			points[to].d = Math.min(points[to].d, d);
		}
		int mid = (from + to) / 2;
		nth_element(points, from, to, mid);
		rec(points, from, mid);
		rec(points, mid, to);
		Event[] events = new Event[mid - from + to - from];
		int cnt = 0;
		for (int i = mid; i < to; i++)
			events[cnt++] = new Event(points[i].y, points[i]);
		int x = points[mid].x;
		for (int i = from; i < to; i++) {

		}
	}

	static long dist(Point a, Point b) {
		long dx = a.x - b.x;
		long dy = a.y - b.y;
		return dx * dx + dy * dy;
	}

	static void nth_element(Point[] a, int low, int high, int n) {
		while (true) {
			int k = randomizedPartition(a, low, high);
			if (n < k)
				high = k;
			else if (n > k)
				low = k + 1;
			else
				return;
		}
	}

	static Random rnd = new Random();

	static int randomizedPartition(Point[] a, int low, int high) {
		swap(a, low + rnd.nextInt(high - low), high - 1);
		Point separator = a[high - 1];
		int i = low - 1;
		for (int j = low; j < high; j++)
			if (a[j].x <= separator.x)
				swap(a, ++i, j);
		return i;
	}

	static void swap(Point[] a, int i, int j) {
		Point t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static class Point {
		int id;
		int x;
		int y;

		long d = Long.MAX_VALUE;

		Point(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}

	static class Event {
		double y;

		Point p;

		Event(double y, Point p) {
			this.y = y;
			this.p = p;
		}
	}


	// random test
	public static void main(String[] args) {
		Random rnd = new Random(1);
		for (int step = 0; step < 1000; step++) {
			int n = rnd.nextInt(100) + 1;
		}
	}
}
