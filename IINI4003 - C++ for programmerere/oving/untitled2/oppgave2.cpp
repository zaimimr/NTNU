#include <iostream>
#include <memory>

using namespace std;

class File {
public:
    string name;
    string suffix;
    File (string text) {
        name = text.substr(0, text.find("."));
        suffix = text.substr(text.find(".")+1, text.size());
    }
    string type(){
        if(suffix == "pdf") {
            return "Portable Document Format";
        } else if (suffix == "txt") {
            return "Plain text";
        } else {
            return "unknown";
        }
    }
};

File * file(string text){
    return new File(text);
}

int main() {
    auto f1 = file("test.pdf");
    cout << f1->type() << endl;
    auto f2 = file("test.txt");
    cout << f2->type() << endl;
    auto f3 = file("test.dat");
    cout << f3->type() << endl;
    auto f4 = file("test");
    cout << f4->type() << endl;
}

