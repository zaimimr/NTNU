#include "fraction.hpp"
#include "Set.hpp"
#include <iostream>
#include <string>
#include <string>

using namespace std;

void print(const string &text, const Fraction &broek) {
    cout << text << broek.numerator << " / " << broek.denominator << endl;
}


void print_set(Set &set) {
    cout << "{";
    for (int x : set.data) {
        cout << to_string(x) + (x == set.data.back() ? "" : ",");
    }
    cout << "}" << endl;
}

void oppgave1() {
    Fraction a(10, 20);
    Fraction b(3, 4);
    Fraction c;
    c.set(5);
    Fraction d = a / b;

    print("a = ", a);
    print("b = ", b);
    print("c = ", c);
    print("d = ", d);

    b += a;
    ++c;
    d *= d;

    print("b = ", b);
    print("c = ", c);
    print("d = ", d);

    c = a + b - d * a;
    c = -c;

    print("c = ", c);

    if (a + b != c + d)
        cout << "a + b != c + d" << endl;
    else
        cout << " a + b == c + d" << endl;
    while (b > a)
        b -= a;
    print("b = ", b);

    //1a)
    Fraction fraction1(3,4);
    Fraction fraction2 = fraction1 - 5;
    print("Fraction 2: ", fraction2);

    Fraction fraction3= 5- fraction1;
    print("Fraction 3: ", fraction3);
}

void oppgave2() {
    vector<int> setA = {3, 4, 10};
    vector<int> setB = {1, 4, 5};
    Set a;
    Set b(setB);
    // Test +Set
    Set c = a + b;
    print_set(c);
    // Test +int
    Set d = a + 1;
    print_set(d);
    // Test =Set
    Set g = c;
    print_set(g);
}
int main() {
    cout << "Oppgave1:" << endl;
    oppgave1();
    cout << "Oppgave2:" << endl;
    oppgave2();
}
