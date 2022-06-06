#include <iostream>

using namespace std;

int a();
int b();


int main() {
    /*
    cout << "Oppgave a:" << endl;
    a();
    cout << "Oppgave b:" << endl;
    b();
    */
    return 0;
}

int a() {
    int i = 3;
    int j = 5;
    int *p = &i;
    int *q = &j;

    cout << "i, variable: " << i << ", address: " << &i << endl;
    cout << "j, variable: " << j << ", address: " << &j << endl;
    cout << "*p, variable: " << p << ", address: " << &p << endl;
    cout << "*q, variable: " << q << ", address: " << &q << endl;

    return 0;
}

int b() {
    int i = 3;
    int j = 5;
    int *p = &i;
    int *q = &j;

    *p = 7;
    *q += 4;
    *q = *p + 1;
    p = q;

    cout << *p << " " << *q << endl;

    return 0;
}