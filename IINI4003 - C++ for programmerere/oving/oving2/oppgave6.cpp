#include <iostream>

using namespace std;

int find_sum(const int *table, int length);

int main() {
    int length = 20;
    const int table[20] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    cout << find_sum(table, 10) << endl;
    cout << find_sum(table + 10, 5) << endl;
    cout << find_sum(table + 15, 5) << endl;
    return 0;
}

int find_sum(const int *table, int length) {
    int sum = 0;
    for (int i = 0; i < length; ++i) {
        sum += table[i];
    }
    return sum;
}