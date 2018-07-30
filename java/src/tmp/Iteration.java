import java.util.List;
import java.util.stream.*;

public class Iteration {
	public static void main(String[] args) {
		int[] array = IntStream.range(0, 5).toArray();
		for (int v : array) {
			System.out.println(v);
		}
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}

		List<Integer> list = IntStream.range(0, 5).boxed().collect(Collectors.toList());
		for (int v : list) {
			System.out.println(v);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		IntStream stream = IntStream.range(0, 5);
		stream.forEach(System.out::println);
	}
}
