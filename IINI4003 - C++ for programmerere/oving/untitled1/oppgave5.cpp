#include <iostream>
#include <vector>

using namespace std;

template<class T>
class Distance {
public:
    T type;
    int ant;

    Distance(int a) {
        ant = a * type.size;
    }
    Distance &operator+=(int number) {
        ant = ant + (number*type.size);
    }

    template<class Type2>
    Distance operator+(const Distance<Type2> &other) const {
        auto res = *this;
        res.ant += other.ant;
        return res;
    }

    friend ostream &operator<<(ostream &ut, const Distance distance) {
        ut << distance.ant;
        return ut;
    }
};

class Meter {
public:
    int size = 1;
};

class Kilometer {
public:
    int size = 1000;
};

int main() {
    Distance<Meter> meters(2);
    meters += 3;
    Distance<Kilometer> kilometers(2);
    kilometers += 3;
    cout << "5 meters: " << meters << endl;
    cout << "5 kilometers: " << kilometers << endl;
    cout << "5 kilometers + 5 meters: " << kilometers + meters << endl;
}
