public class Z {
	static abstract class NumberIterator {
		protected final long base;
		protected final long[] power;

		protected NumberIterator() {
			this(10);
		}

		protected NumberIterator(int base) {
			this.base = base;
			power = generatePowers(base, Long.MAX_VALUE);
		}

		protected abstract void process(long prefix, int remainingDigits);

		public void run(long from, long to) {
			if (from < 0 || from > to)
				throw new IllegalArgumentException();
			to++;
			for (int i = 0; ; i++) {
				if (i != power.length - 1 && from / power[i + 1] != to / power[i + 1]) {
					long prefix = from / power[i + 1] * base;
					for (int j = (int) (from / power[i] % base); j < base; j++)
						process(prefix + j, i);
					from /= power[i + 1];
					from++;
					from *= power[i + 1];
				} else {
					long upTo = to / power[i] % base;
					long prefix = from / power[i] / base * base;
					for (int j = (int) (from / power[i] % base); j < upTo; j++) {
						process(prefix + j, i);
					}
					for (int k = i - 1; k >= 0; k--) {
						upTo = to / power[k] % base;
						prefix = to / power[k + 1] * base;
						for (int j = 0; j < upTo; j++) {
							process(prefix + j, k);
						}
					}
					break;
				}
			}
		}
	}

	public static long[] generatePowers(long base, long maxValue) {
		if (maxValue <= 0)
			return new long[0];
		int size = 1;
		long current = 1;
		while (maxValue / base >= current) {
			current *= base;
			size++;
		}
		return generatePowers(base, size, Long.MAX_VALUE);
	}

	public static long[] generatePowers(long base, int count, long mod) {
		long[] result = new long[count];
		if (count != 0)
			result[0] = 1 % mod;
		for (int i = 1; i < count; i++)
			result[i] = result[i - 1] * base % mod;
		return result;
	}


	public static void main(String[] args) {
		NumberIterator numberIterator = new NumberIterator() {
			@Override
			protected void process(long prefix, int remainingDigits) {
				System.out.println(prefix + " " + remainingDigits);
			}
		};
		numberIterator.run(1, 115);
	}

}
