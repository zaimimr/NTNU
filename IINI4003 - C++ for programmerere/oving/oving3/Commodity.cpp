const double moms = 0.25;

#include <iostream>
#include <string>

using namespace std;

class Commodity {
public:
    Commodity(string name_, int id_, double price_); // objektet må hete det samme som klassenavnet, nemlig Circle og ikke circle
    string get_name() const;
    int get_id() const;
    double get_price(int quantity_) const;
    void set_price(double price_);
    double get_price_with_sales_tax(int quantity_) const;
private:
    string name;
    int id;
    double price;
};

// ==> Implementasjon av klassen Commodity
Commodity::Commodity(string name_, int id_, double price_) : name(name_), id(id_), price(price_) {}

string Commodity::get_name() const {
    return name;
}
int Commodity::get_id() const {
    return id;
}
double Commodity::get_price(int quantity = 1) const {
    return price * quantity;
}
void Commodity::set_price(double price_) {
    price = price_;
}
double Commodity::get_price_with_sales_tax(int quantity = 1) const {
    return (price + price * moms) * quantity;
}
