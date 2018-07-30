#pragma comment(linker, "/STACK:268435456")

#include <algorithm>
#include <iostream>
#include <future>

using namespace std;

vector<int> solve(vector<int> x, vector<int> y) {
	int n = x.size();
	vector<int> res(n);
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
				long long cross = (long long) x1 * y2 - (long long) x2 * y1;
				if (cross <= 0) ++cnt1;
				if (cross >= 0) ++cnt2;
			}
			cnt = max(cnt, max(cnt1, cnt2));
		}
		res[i] = n == 1 ? 0 : n - cnt;
	}
	return res;
}

int main() {
	auto start = chrono::system_clock::now();

	string name = "C-large-practice";
	string path = "";

	freopen((path + name + ".in").c_str(), "r", stdin);
	freopen((path + name + ".out").c_str(), "w", stdout);

	int test_cases;
	cin >> test_cases;

	future<vector<int>> futures[test_cases];

	for (int test_case = 1; test_case <= test_cases; test_case++) {
		int n;
		cin >> n;
		vector<int> x(n);
		vector<int> y(n);
		for (int i = 0; i < n; i++) {
			cin >> x[i];
			cin >> y[i];
		}

		futures[test_case - 1] = async(launch::async, solve, x, y);
	}

	for (int test_case = 1; test_case <= test_cases; test_case++) {
		vector<int> res = futures[test_case - 1].get();
		cout << "Case #" << test_case << ": " << endl;
		for (int v : res) {
			cout << v << endl;
		}
		cout.flush();
	}

	fclose(stdout);
	fclose(stdin);

	cerr << chrono::duration_cast<chrono::milliseconds>(chrono::system_clock::now() - start).count() << endl;
	return 0;
}
