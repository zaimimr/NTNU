#include <iostream>
#include <vector>

using namespace std;

class Animal {
public:
    virtual string produce() = 0;
    virtual ~Animal() {}
};

class Chicken : public Animal {
public:
    string produce() override { return "egg";}
};

class Cow : public Animal {
public:
    string produce() override { return "milk";}
};

class Sheep : public Animal {
public:
    string produce() override { return "wool";}
};

class Farm {
public:
    vector<unique_ptr<Animal>> animals;

    void add_animal(const string &text) {
        if(text == "chicken") {
            animals.push_back(Chicken())
        } else if (text == "cow") {
            animals.push_back(Cow())
        } else if (text == "sheep") {
            animals.push_back(Sheep())
        }
    }
};

int main() {
    Farm farm;
    farm.add_animal("chicken");
    farm.add_animal("chicken");
    farm.add_animal("chicken");
    farm.add_animal("cow");
    farm.add_animal("sheep");
    farm.add_animal("sheep");
    cout << "Farm production: " << endl;
    for (auto &animal : farm.animals)
        cout << animal->produce() << endl;
}
