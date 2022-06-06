//
// Created by zaim on 11.10.2020.
//

#include "Set.hpp"
#include <vector>

using namespace std;

Set::Set() {
    data = vector<int>();
};

Set::Set(vector<int> data_) {
    data = data_;
};

Set Set::operator+(const Set &other) const {
    Set newSet = *this;
    for(int integer : other.data) {
        bool exist = false;
        for(int number : newSet.data) {
            if(number == integer) exist = true;
        }
        if(!exist){
            newSet.data.emplace_back(integer);
        }
    }
    return newSet;
}

Set Set::operator+(int integer) const {
    Set newSet = *this;
    for (int number : newSet.data) {
        if (number == integer) {
            return newSet;
        }
    }
    newSet.data.emplace_back(integer);
    return newSet;
}

Set Set::operator=(const Set &other) const {
    Set newSet = *this;
    newSet.data.clear();
    for (int integer : other.data) {
        newSet.data.emplace_back(integer);
    }
    return newSet;
}