package tmp;
import java.util.*;

public class LcaOfflineQueries {

	int[] p;
	int[] ancestor;
	boolean[] visited;
	List<Integer>[] tree;
	List<Integer>[] queries;

	public LcaOfflineQueries(List<Integer>[] tree, List<Integer>[] queries) {
		int n = tree.length;
		p = new int[n];
		ancestor = new int[n];
		visited = new boolean[n];
		this.tree = tree;
		this.queries = queries;
	}

	int root(int x) {
		return x == p[x] ? x : (p[x] = root(p[x]));
	}

	void unite(int a, int b, int newAncestor) {
		a = root(a);
		b = root(b);
		if (a != b)
			p[a] = b;
		ancestor[b] = newAncestor;
	}

	void dfs(int u) {
		p[u] = u;
		ancestor[u] = u;
		visited[u] = true;
		for (int v : tree[u]) {
			if (!visited[v]) {
				dfs(v);
				unite(u, v, u);
			}
		}
		for (int v : queries[u]) {
			if (visited[v]) {
				System.out.printf("%d %d -> %d\n", u + 1, v + 1, ancestor[root(v)] + 1);
			}
		}
	}

	public static void main(String[] args) {
		int n = 5;
		List<Integer>[] tree = new List[n];
		for (int i = 0; i < n; i++) {
			tree[i] = new ArrayList<>();
		}
		tree[0].add(1);
		tree[1].add(0);
		tree[1].add(2);
		tree[2].add(1);
		tree[3].add(1);
		tree[1].add(3);
		tree[0].add(4);
		tree[4].add(0);

		new LcaOfflineQueries(tree, null);
	}
}
