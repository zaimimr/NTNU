#include <algorithm>
#include <iostream>
#include <vector>
#include <gtkmm.h>

using namespace std;

int oppgave1();

class Window : public Gtk::Window {
public:
    Gtk::VBox vbox;
    Gtk::Entry entry;
    Gtk::Button button;
    Gtk::Label label;

    Window() {
        button.set_label("Click here");

        vbox.pack_start(entry);  //Add the widget entry to vbox
        vbox.pack_start(button); //Add the widget button to vbox
        vbox.pack_start(label);  //Add the widget label to vbox

        add(vbox);  //Add vbox to window
        show_all(); //Show all widgets

        entry.signal_changed().connect([this]() {
            label.set_text("Entry now contains: " + entry.get_text());
        });

        entry.signal_activate().connect([this]() {
            label.set_text("Entry activated");
        });

        button.signal_clicked().connect([this]() {
            label.set_text("Button clicked");
        });
    }
};

int main() {
    oppgave1();
}

int oppgave1(){
    vector <double> v { 1.0, 2.0, 3.0, 4.0, 5.0};
    cout << v.front() << endl;
    cout << v.back() << endl;
    v.emplace(v.begin(), 300.2);
    cout << v.front() << endl;
    vector<double>::iterator found = find(v.begin(), v.end(), 4.0);
    if(found != v.end()) {
        cout << *found << endl;
    }
    return 0;
}
int oppgave2(){
    Gtk::Main gtk_main;
    Window window;
    gtk_main.run(window);
}