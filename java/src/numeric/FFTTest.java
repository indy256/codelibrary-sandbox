public class FFTTest {
	public static void main(String[] args) {
//		double[] a = {-10.6, 0.1, 0.2, -1.04, 0.18, -0.6};
//		double[] b = {-7.64, 0.34, 1.00, -1.04, -1.16};
//		double[] y = {6.8, 9.0, 8.6, 17.4, 26.6, 27.6, 32.2, 31.0, 28.2, 17.8, 14.8, 7.2};
		double[] y = {6.8, 9.0, 8.6, 17.4, 26.6, 27.6, 32.2, 31.0, 28.2, 17.8};
//		double[] b = new double[a.length];
//		FFT.fft(a, b, false);
//		int z=1;

		int n = y.length;
		double[] a = new double[y.length];
		double[] b = new double[y.length];
		for (int k = 0; k < y.length; k++) {
			a[k] = 0;
			b[k] = 0;
			for (int j = 0; j < y.length; j += 1) {
				a[k] += 2. / n * y[j] * Math.cos(k * 2 * Math.PI * (j + 1) / n);
				b[k] += 2. / n * y[j] * Math.sin(k * 2 * Math.PI * (j + 1) / n);
			}
		}

		int z = 1;

	}
}
