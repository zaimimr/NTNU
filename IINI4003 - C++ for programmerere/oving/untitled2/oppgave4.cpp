//
// Created by zaim on 09.12.2020.
//
#include <iostream>

using namespace std;

class Number {
public:
    virtual int nr () = 0;
    virtual ~Number() {}

    template <class T>
    friend ostream &operator<<(ostream &ut, T number) {
        ut << number.nr();
        return ut;
    }
};

class One : public Number{
public:
    int nr() override {
        return 1;
    }
    template<class T1>
    int operator*(T1 &otherNr) {
        auto me = *this;


    }

    template<class Ta, class Tb>
    friend int operator+(Ta &me, Tb &otherNr) {
        return me.nr() * otherNr.nr();
    }
};

class Two : public Number {
public:
    int nr() override {
        return 2;
    }
};

int main() {
    One one;
    Two two;
    cout << one << endl;
    cout << two * one << endl;
    cout << two * one + one << endl;
    cout << -(two * one + one) << endl;
}