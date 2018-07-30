#include <vector>
#include <iostream>
#include <thread>
#include <future>

using namespace std;

void f1() {
	cout << "f1" << endl;
}

void f2(int x) {
	cout << "f2(" << x << ")" << endl;
}

bool f(int v) {
	this_thread::sleep_for(chrono::seconds(v));
	cout << "f(" << v << ")" << endl;
	return true;
}

int main() {
	cout << "number of processors: " << thread::hardware_concurrency() << endl;

	std::thread thread1(f1);
	std::thread thread2(f2, 5);
	thread1.join();
	thread2.join();
	cout << "threads completed" << endl;

	future<bool> futures[3];
	for (int i = 0; i < 3; i++) {
		futures[i] = async(launch::async, f, i + 1);
	}

	cout << "futures started" << endl;

	for (int i = 0; i < 3; i++) {
		bool b = futures[i].get();
	}

	cout << "futures completed" << endl;

	return 0;
}
