import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class ConvertLFCR {
	public static void main(String[] args) throws Exception {
		Path path = new File(args[0]).toPath();
		byte[] a = Files.readAllBytes(path);
		byte[] b = new byte[a.length];
		int cnt = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0xD) {
				b[cnt++] = a[i];
			}
		}
		b = Arrays.copyOf(b, cnt);
		Files.write(path, b, StandardOpenOption.TRUNCATE_EXISTING);
	}
}
