#include <gtkmm.h>

using namespace std;

class Window : public Gtk::Window {
public:
  Gtk::VBox vbox;
  Gtk::Button button;
  Gtk::Label label;
  Gtk::Entry first_name;
  Gtk::Entry last_name;

  bool is_first_name = false;
  bool is_last_name = false;

  Window() {
    button.set_label("Combine name");
    button.set_sensitive(false);

    vbox.pack_start(first_name);
    vbox.pack_start(last_name);
    vbox.pack_start(button); //Add the widget button to vbox
    vbox.pack_start(label);  //Add the widget label to vbox

    add(vbox);  //Add vbox to window
    show_all(); //Show all widgets

    first_name.signal_changed().connect([this]() {
      is_first_name = first_name.get_text_length() != 0;
      button.set_sensitive(is_first_name && is_last_name);
    });
    last_name.signal_changed().connect([this]() {
      is_last_name = last_name.get_text_length() != 0;
      button.set_sensitive(is_first_name && is_last_name);
    });
    button.signal_clicked().connect([this]() {
      label.set_text("Combined names:" + first_name.get_text() + " " + last_name.get_text());
    });
  }
};

int main() {
  Gtk::Main gtk_main;
  Window window;
  gtk_main.run(window);
}
