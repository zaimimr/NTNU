#include <iostream>

using namespace std;

int main() {
    double number = 1;
    double *pointer = &number;
    double &ref = number;

    number = 4;
    cout << number << endl;
    *pointer = 5;
    cout << number << endl;
    ref = 2;
    cout << number << endl;
    return 0;
}
