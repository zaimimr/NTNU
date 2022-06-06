#include <iostream>
#include <string>
#include <thread>
#include <vector>
using namespace std;

//Oppgave 1
void swap(int &a, int &b) {
    auto temp = a;
    a = b;
    b = temp;
}

void swap(int *a, int *b) {
    auto temp = *a;
    *a = *b;
    *b = temp;
}

void swap(int *a, int &d) {
    auto temp = *a;
    *a = d;
    d = temp;
}

class Hot {
    friend ostream &operator<<(ostream &ut, const Hot &) {
        ut << "hot";
        return ut;
    }
};

class Cold {
    friend ostream &operator<<(ostream &ut, const Cold &) {
        ut << "cold";
        return ut;
    }
};

class Electric {
public:
    string name = "electric";
};
class Gasoline {
public:
    string name = "gasoline";
};

class Car {
public:
    ~Car(){};
    virtual string name() = 0;
    virtual string engineType() = 0;
    friend ostream &operator<<(ostream &ut, Car &car) {
        ut << car.name() << " with " << car.engineType() << " engine";
        return ut;
    }
};



template<class T>
class Volvo : public Car {
private:
    T type;
public:
    string name() override {return "volvo";}
    string engineType() override {return type.name;}
};
template<class T>
class Saab : public Car {
private:
    T type;
public:
    string name() override {return "saab";}
    string engineType() override {return type.name;}
};

//Oppgave 2
class Degrees {
public:
    Cold operator-() {
        Cold cold;
        return cold;
    }
    Hot operator+() {
        Hot hot;
        return hot;
    }
};

int main() {
    //Oppgave 1
    int a = 1, b = 2;
    swap(a, b);
    std::cout << a << ' ' << b << std::endl;
    swap(&a, &b);
    std::cout << a << ' ' << b << std::endl;
    auto c = &a;
    auto d = &b;
    swap(c, *d);
    std::cout << *c << ' ' << *d << std::endl;

    //Oppgave 2
    Degrees degrees;
    cout << +degrees << endl;
    cout << -degrees << endl;
    // Oppgave 3
    thread *a_thread;
    {
        int a = 2;
        auto b = make_unique<int>(2);
        a_thread = new std::thread([&a, b=move(b)] {
            std::cout << (a + *b) << std::endl;
        });
    }
    a_thread->join();

    // Oppgave 4
    vector<unique_ptr<Car>> cars;
    cars.emplace_back(new Saab<Electric>());
    cars.emplace_back(new Saab<Gasoline>());
    cars.emplace_back(new Volvo<Electric>());
    for (auto &car : cars)
        cout << *car << endl;
}
