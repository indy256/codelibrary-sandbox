import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TemplateMT {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String name = "C-large-practice";
		String path = "";

		Locale.setDefault(Locale.US);

		Scanner sc = new Scanner(new File(path + name + ".in"));
		PrintWriter pw = new PrintWriter(path + name + ".out");
//		Scanner sc = new Scanner(System.in);
//		PrintWriter pw = new PrintWriter(System.out);

		ExecutorService executor = Executors.newFixedThreadPool(16);

		int testCases = sc.nextInt();
		Future<int[]>[] futures = new Future[testCases];
		Set<Integer> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
		for (int testCase = 1; testCase <= testCases; testCase++) {
			int n = sc.nextInt();
			int[] x = new int[n];
			int[] y = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = sc.nextInt();
				y[i] = sc.nextInt();
			}
			int cur = testCase;

			futures[testCase - 1] = executor.submit(() -> {
				set.add(cur);
				System.out.println(set);
				int[] res = new int[n];
				for (int i = 0; i < n; i++) {
					int cnt = 0;
					for (int j = 0; j < n; j++) {
						if (i == j) continue;
						int x1 = x[j] - x[i];
						int y1 = y[j] - y[i];
						int cnt1 = 0;
						int cnt2 = 0;
						for (int k = 0; k < n; k++) {
							int x2 = x[k] - x[i];
							int y2 = y[k] - y[i];
							long cross = (long) x1 * y2 - (long) x2 * y1;
							if (cross <= 0) ++cnt1;
							if (cross >= 0) ++cnt2;
						}
						cnt = Math.max(cnt, Math.max(cnt1, cnt2));
					}
					res[i] = n == 1 ? 0 : n - cnt;
				}
				set.remove(cur);
				System.out.println(set);
				return res;
			});
		}

		for (int testCase = 1; testCase <= testCases; testCase++) {
			int[] res = futures[testCase - 1].get();
			pw.println("Case #" + testCase + ":");
			for (int v : res) {
				pw.println(v);
			}
			pw.flush();
		}

		pw.close();
		sc.close();
		executor.shutdown();
		System.err.println(System.currentTimeMillis() - start);
	}
}
