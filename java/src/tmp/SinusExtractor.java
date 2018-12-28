package tmp;
public class SinusExtractor {

	static class Sinus {
		double f;
		double a;
		double ph;

		Sinus(double f, double a, double ph) {
			this.f = f;
			this.a = a;
			this.ph = ph;
		}
	}

	public static void main(String[] args) {
		int n = 100;
		double[] a = new double[n];
		for (int i = 0; i < n; i++) {
			a[i] = 44 * Math.sin(3.7 * 2 * Math.PI * i / n + 0.1) + 3 * Math.sin(5.3 * 2 * Math.PI * i / n + 0.3) + 7 * Math.sin(1.9 * 2 * Math.PI * i / n + 0.7);
		}

		double bestE = 0;
		Sinus bestSinus = null;

		for (double f1 = 0; f1 <= 10; f1 += 0.01) {
			System.out.println(f1);
			for (double a1 = 1; a1 <= 1; a1 += 0.1) {
				for (double ph1 = 0; ph1 <= Math.PI; ph1 += 0.02) {
					double curE = 0;
					for (int i = 0; i < n; i++) {
						double v = a1 * Math.sin(2 * Math.PI * i / n * f1 + ph1);
//						double diff = a[i] - v;
//						curE += diff * diff;
//						curE += Math.abs(diff);
						curE -= a[i]*v;
					}
					if (bestSinus == null || bestE > curE) {
						bestE = curE;
						bestSinus = new Sinus(f1, -bestE *2/n, ph1);
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			double v = bestSinus.a * Math.sin(2 * Math.PI * i / n * bestSinus.f + bestSinus.ph);
			a[i]-=v;
		}

		 bestE = 0;
		 bestSinus = null;

		for (double f1 = 0; f1 <= 10; f1 += 0.1) {
			System.out.println(f1);
			for (double a1 = 0; a1 <= 10; a1 += 0.1) {
				for (double ph1 = 0; ph1 <= Math.PI; ph1 += 0.05) {
					double curE = 0;
					for (int i = 0; i < n; i++) {
						double v = a1 * Math.sin(2 * Math.PI * i / n * f1 + ph1);
						double diff = a[i] - v;
//						curE += diff * diff;
						curE += Math.abs(diff);
//						curE += a[i]*v;
					}
					if (bestSinus == null || bestE > curE) {
						bestE = curE;
						bestSinus = new Sinus(f1, a1, ph1);
					}
				}
			}
		}

		int z = 1;
	}
}
