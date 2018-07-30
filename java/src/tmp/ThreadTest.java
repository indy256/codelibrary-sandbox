public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
					Thread.currentThread().interrupt();
				}
			}
		});
		t.start();
		Thread.sleep(200);
		t.interrupt();
	}
}
