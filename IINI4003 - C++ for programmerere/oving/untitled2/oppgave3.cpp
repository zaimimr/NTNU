#include <iostream>
#include <thread>
#include <vector>
#include <functional>
#include <mutex>

using namespace std;

template<typename T>
class ThreadSafeVector {
    vector <T> vec;
    mutex vec_mutex;
    //vector<function<void(int e)>> vec;
public:
    void emplace_back(int thread_id) {
        lock_guard<mutex> lock(vec_mutex);
        vec.template emplace_back(thread_id);
    }

    void for_each(function<void(int e)> lambda){
        lock_guard<mutex> lock(vec_mutex);
        for (auto &thread_id : vec)
            lambda(thread_id);
    }
};

int main() {
    ThreadSafeVector<int> safe_vec;
    vector <thread> threads;
    for (int thread_id = 0; thread_id < 4; ++thread_id) {
        threads.emplace_back([thread_id, &safe_vec] {
            safe_vec.emplace_back(thread_id);

        });
    }
    for (auto &thread : threads) thread.join();
    string result;
    threads.clear();
    for (int thread_id = 0; thread_id < 4; ++thread_id) {
        threads.emplace_back([&result, &safe_vec] {
            safe_vec.for_each([&result](int e) { result += to_string(e); });
        });
    }
    for (auto &thread : threads) thread.join();
    cout << result << endl;
}