#include <iostream>
#include <string>
#include <vector>

#include "Circle.cpp"
#include "Commodity.cpp"

using namespace std;

void oppgave2();
void oppgave3();
void oppgave4();



int main() {
    oppgave2();
    oppgave3();
    oppgave4();
}

void oppgave2(){
    cout << "Oppgave 2: " << endl;

    Circle circle(5);

    cout << "Hello" << endl;

    int area = circle.get_area();
    cout << "Arealet er lik " << area << endl;

    double circumference = circle.get_circumference();
    cout << "Omkretsen er lik " << circumference << endl;
}
void oppgave3(){
    cout << "Oppgave 3: " << endl;

    const double quantity = 2.5;
    Commodity commodity("Norvegia", 123, 73.50);

    cout << "Varenavn: " << commodity.get_name() << ", varenr: " << commodity.get_id() << " Pris pr enhet: " << commodity.get_price() << endl;

    cout << "Kilopris: " << commodity.get_price() << endl;
    cout << "Prisen for " << quantity << " kg er " << commodity.get_price(quantity) << " uten moms" << endl;
    cout << "Prisen for " << quantity << " kg er " << commodity.get_price_with_sales_tax(quantity) << " med moms" << endl;

    commodity.set_price(79.60);
    cout << "Ny kilopris: " << commodity.get_price() << endl;
    cout << "Prisen for " << quantity << " kg er " << commodity.get_price(quantity) << " uten moms" << endl;
    cout << "Prisen for " << quantity << " kg er " << commodity.get_price_with_sales_tax(quantity) << " med moms" << endl;
}
void oppgave4(){
    cout << "oppgave4:" << endl;
    string word1, word2, word3;
    cout << "Ord1:" << endl;
    cin >> word1;
    cout << "Ord2:" << endl;
    cin >> word2;
    cout << "Ord3:" << endl;
    cin >> word3;
    string sentence = word1 + " " + word2 + " " + word3 + ".";
    cout << sentence << endl;
    cout << "Lengden til ordenen: " << endl;
    cout << word1 << ":" << word1.length() << endl;
    cout << word2 << ":" << word2.length() << endl;
    cout << word3 << ":" << word3.length() << endl;
    cout << sentence << ":" << sentence.length() << endl;
    string sentence2 = sentence;
    sentence2.replace(9,3, "xxx", 3);
    cout << sentence << " " << sentence2 << endl;
    string sentence_start = sentence.substr(0, 5);
    cout << sentence << " " << sentence_start << endl;
    size_t pos1 = sentence_start.find("hallo");
    if (pos1!=string::npos){
        cout << "hallo funnet:" << pos1 << endl;
    } else {
        cout << "hallo ikke funnet" << endl;
    }

    vector<size_t> positions;
    size_t pos2 = sentence.find("er", 0);
    while(pos2 != string::npos) {
        cout << "'er' funnet: " << pos2 << endl;
        positions.push_back(pos2);
        pos2 = sentence.find("er",pos2+1);
    }
}

