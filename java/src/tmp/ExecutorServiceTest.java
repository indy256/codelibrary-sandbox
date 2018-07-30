import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceTest {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 5; i++) {
			int j = i;
			executorService.execute(() -> {
				System.out.println(">" + j);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}
				System.out.println("<" + j);
			});
		}
		executorService.shutdown();
	}
}
