const double pi = 3.141592;

#include <iostream>
#include <string>

using namespace std;

class Circle {
public:
    Circle(double radius_); // objektet må hete det samme som klassenavnet, nemlig Circle og ikke circle
    int get_area() const;
    double get_circumference() const;
private:
    double radius;
};

// ==> Implementasjon av klassen Circle
Circle::Circle(double radius_) : radius(radius_) {}

int Circle::get_area() const {
    return pi * radius * radius;
}

double Circle::get_circumference() const {
    double circumference = 2.0 * pi * radius;
    return circumference;
}