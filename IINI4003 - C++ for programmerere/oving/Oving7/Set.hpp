//
// Created by zaim on 11.10.2020.
//
#include <vector>

using namespace std;

class Set {
public:
    vector<int> data;
    Set();
    Set(vector<int> data);
    Set operator+(const Set &other) const;
    Set operator+(int Int) const;
    Set operator=(const Set &other) const;
};
