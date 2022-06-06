#include <iostream>

using namespace std;

int main() {
    int a = 5;
    int &b = a; // En referanse variabel må blir initialisert
    int *c;
    c = &b;
    a = b + *c; // Peke til referanse variablen b og ikke pekeren
    b = 2; // Må til b og ikke &b
    return 0;
}
