package tmp;
import java.util.*;

public class HopcroftKarp {

	static class V implements Iterable<V> {
		List<V> adj = new ArrayList<>();
		int level;
		boolean used;
		V pair;

		@Override
		public Iterator<V> iterator() {
			return adj.iterator();
		}
	}

	public static int hopcroftKarp(V[] vs) {
		for (int match = 0; ; ) {
			Queue<V> q = new LinkedList<>();
			for (V v : vs) v.level = -1;
			for (V v : vs)
				if (v.pair == null) {
					v.level = 0;
					q.add(v);
				}
			while (!q.isEmpty()) {
				V v = q.remove();
				for (V u : v) {
					V w = u.pair;
					if (w != null && w.level < 0) {
						w.level = v.level + 1;
						q.add(w);
					}
				}
			}
			for (V v : vs) v.used = false;
			int d = 0;
			for (V v : vs) if (v.pair == null && dfs(v)) d++;
			if (d == 0) return match;
			match += d;
		}
	}

	static boolean dfs(V v) {
		v.used = true;
		for (V u : v) {
			V w = u.pair;
			if (w == null || !w.used && v.level < w.level && dfs(w)) {
				v.pair = u;
				u.pair = v;
				return true;
			}
		}
		return false;
	}

	// random test
	public static void main(String[] args) {

	}
}
