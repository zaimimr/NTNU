#include <iostream>

using namespace std;

template <typename Type>
bool equal(Type a, Type b) {
    return a == b;
}

bool equal(double a, double b) {
    return abs(a-b) < 0.00001;
}

void oppgave1(){
    cout << (equal("test", "test") == true) << endl;
    cout << (equal("test", "tester") == false) << endl;
    cout << (equal(0.000001, 0.000002) == true) << endl;
    cout << (equal(0.00001, 0.00002) == false) << endl;
}

template <typename TypeA, typename TypeB>
class Pair {
public:
    TypeA first;
    TypeB second;

    Pair(TypeA first, TypeB b): first(first), second(second) {}

    Pair operator+(const Pair &other) {
        Pair p = *this;
        p.first += other.first;
        p.second += other.second;
        return p;
    }

    bool operator>(const Pair &other) {
        Pair pair = *this;
        auto thisSum = pair.first + pair.second;
        auto otherSum = other.first + other.second;
        return thisSum > otherSum;
    }

};

void oppgave2(){
    Pair<double, int> p1(3.5, 14);
    Pair<double, int> p2(2.1, 7);
    cout << "p1: " << p1.first << ", " << p1.second << endl;
    cout << "p2: " << p2.first << ", " << p2.second << endl;

    if (p1 > p2)
        cout << "p1 er størst" << endl;
    else
        cout << "p2 er størst" << endl;

    auto sum = p1 + p2;
    cout << "Sum: " << sum.first << ", " << sum.second << endl;
}


int main() {
    oppgave1();
    oppgave2();
}