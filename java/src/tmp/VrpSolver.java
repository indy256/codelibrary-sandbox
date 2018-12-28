package tmp;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VrpSolver extends JFrame {

	Random rnd = new Random(1);
	int n = 200;
	Point[] points = new Point[n + 1];
	int m = 10;
	List<Integer>[] bestTours;
	Cost[] costs = new Cost[m];
	final int CAPACITY = 1000;
	final double MAX_LENGTH = 3;

	{
		for (int i = 0; i < n + 1; i++)
			points[i] = new Point(rnd.nextDouble(), rnd.nextDouble(), i == 0 ? 0 : rnd.nextInt(20) + 1);
	}

	Cost getCost(List<Integer> tourList) {
		Integer[] tour = tourList.toArray(new Integer[0]);
		double weight = 0;
		double length = 0;
		for (int i = 0, j = tour.length - 1; i < tour.length; j = i++) {
			weight += points[tour[i]].weight;
			length += dist(points[tour[i]].x, points[tour[i]].y, points[tour[j]].x, points[tour[j]].y);
		}
		return new Cost(Math.max(0, weight - CAPACITY), Math.max(0, length - MAX_LENGTH), length, weight);
	}

	Cost getCost(List<Integer>[] tours) {
		Cost cost = new Cost(0, 0, 0, 0);
		for (List<Integer> tour : tours)
			cost = cost.add(getCost(tour));
		return cost;
	}

	static double dist(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}


	public void optimize() {
		List<Integer>[] tours = new List[m];
		for (int i = 0, cur = 0; i < m; i++) {
			tours[i] = new ArrayList<>();
			tours[i].add(0);
			for (int j = 0; j < n / m + (i < n % m ? 1 : 0); j++)
				tours[i].add(++cur);
			costs[i] = getCost(tours[i]);
		}
		while (true) {
			localSearch(tours);
			List<List<Integer>> newTours = new ArrayList<>();
			for (List<Integer> tour : tours)
				if (tour.size() > 1)
					newTours.add(tour);
			Cost cost = getCost(newTours.toArray(new List[0]));
			bestTours = new List[newTours.size()];
			for (int i = 0; i < bestTours.length; i++) bestTours[i] = new ArrayList<>(newTours.get(i));
			repaint();
			if (newTours.size() == 1 || cost.weightExcess > 1e-9 || cost.lengthExcess > 1e-9)
				break;
			int cnt = newTours.size();
			for (int i = 0; i < newTours.get(cnt - 1).size(); i++) {
				int v = newTours.get(cnt - 1).get(i);
				if (v == 0) continue;
				newTours.get(i % (cnt - 1)).add(v);
			}
			newTours.remove(cnt - 1);
			tours = newTours.toArray(new List[0]);
		}
	}

	void localSearch(List<Integer>[] tours) {
		int iterations = 0;
		for (boolean improved = true; improved; iterations++) {
			improved = false;

			for (int k = 0; k < tours.length; k++) {
				for (int i = 0; i < tours[k].size(); i++) {
					for (int j = 0; j < tours[k].size(); j++) {
						List<Integer> ntour = new ArrayList<>(tours[k]);
						reverse(ntour, i, j);
						Cost cost = getCost(tours[k]);
						Cost ncost = getCost(ntour);
						if (ncost.compareTo(cost) < 0) {
							tours[k] = ntour;
							improved = true;
							repaint();
						}
					}
				}
			}

			for (int k1 = 0; k1 < tours.length; k1++) {
				for (int k2 = 0; k2 < tours.length; k2++) {
					if (k1 == k2) continue;
					m1:
					for (int i = 0; i < tours[k1].size(); i++) {
						if (tours[k1].get(i) == 0) continue;
						for (int j = 0; j < tours[k2].size(); j++) {
							List<Integer> ntour1 = new ArrayList<>(tours[k1]);
							List<Integer> ntour2 = new ArrayList<>(tours[k2]);
							int v = ntour1.remove(i);
							ntour2.add(j, v);
							Cost cost = getCost(tours[k1]).add(getCost(tours[k2]));
							Cost ncost = getCost(ntour1).add(getCost(ntour2));
							if (ncost.compareTo(cost) < 0) {
								tours[k1] = ntour1;
								tours[k2] = ntour2;
								improved = true;
								repaint();
								continue m1;
							}
						}
					}
				}
			}
		}
		System.out.println(iterations);
	}

	static void reverse(List<Integer> p, int i, int j) {
		int n = p.size();
		while (i != j) {
			p.set(i, p.set(j, p.get(i)));
			i = (i + 1) % n;
			if (i == j) break;
			j = (j - 1 + n) % n;
		}
	}

	static class Point {
		final double x;
		final double y;
		final int weight;

		Point(double x, double y, int weight) {
			this.x = x;
			this.y = y;
			this.weight = weight;
		}
	}


	static class Cost implements Comparable<Cost> {
		final double weightExcess;
		final double lengthExcess;
		final double length;
		final double weight;

		Cost(double weightExcess, double lengthExcess, double length, double weight) {
			this.weightExcess = weightExcess;
			this.lengthExcess = lengthExcess;
			this.length = length;
			this.weight = weight;
		}

		public Cost add(Cost cost) {
			return new Cost(weightExcess + cost.weightExcess, lengthExcess + cost.lengthExcess, length + cost.length, weight + cost.weightExcess);
		}

		@Override
		public int compareTo(Cost o) {
			int delta;
			if ((delta = Double.compare(weightExcess, o.weightExcess)) != 0) return delta < 0 ? -1 : 1;
			if ((delta = Double.compare(lengthExcess, o.lengthExcess)) != 0) return delta < 0 ? -1 : 1;
			return Double.compare(length, o.length);
		}
	}

	public VrpSolver() {
		setContentPane(new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (bestTours == null) return;
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setStroke(new BasicStroke(3));
				int w = getWidth() - 5;
				int h = getHeight() - 330;
				Color[] colors = {Color.BLUE, Color.RED, Color.BLACK, Color.PINK, Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.ORANGE, Color.MAGENTA};
				List<Integer>[] tours = bestTours;
				for (int k = 0; k < tours.length; k++) {
					g.setColor(colors[k % colors.length]);
					for (int i = 0, j = tours[k].size() - 1; i < tours[k].size(); j = i++)
						g.drawLine((int) (points[tours[k].get(i)].x * w), (int) ((1 - points[tours[k].get(i)].y) * h),
								(int) (points[tours[k].get(j)].x * w), (int) ((1 - points[tours[k].get(j)].y) * h));
				}
				g.setColor(Color.BLACK);
				Cost cost = getCost(tours);
				g.drawString(String.format("tours: %d    weightExcess: %.0f    lengthExcess: %.4f    length: %.3f", tours.length, cost.weightExcess, cost.lengthExcess, cost.length), 5, h + 20);
				for (int i = 0; i < tours.length; i++) {
					cost = getCost(tours[i]);
					g.drawString(String.format("weightExcess: %.0f    lengthExcess: %.4f    length: %.3f    weight: %.0f", cost.weightExcess, cost.lengthExcess, cost.length, cost.weight), 5, h + 20 * (i + 2));
				}
			}
		});
		setSize(new Dimension(600, 900));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		new Thread(this::optimize).start();
	}

	public static void main(String[] args) {
		new VrpSolver();
	}

}
