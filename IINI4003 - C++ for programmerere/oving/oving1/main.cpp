#include <iostream>
#include <cstring>
#include <cstdlib>
#include <fstream>
#include <math.h>

using namespace std;

int oppgaveA();

int oppgaveB();

int read_temperatures(double temperatures[], int length);

int main() {
    cout << "###Oppgave A###" << endl;
    oppgaveA();
    cout << "###Oppgave B###" << endl;
    oppgaveB();
    return 0;
}


int oppgaveA() {
    const int length = 5;
    int under_10 = 0;
    int between_10_and_20 = 0;
    int over_20 = 0;
    double user_input = 0;
    cout << "Du skal skrive inn " << length << " temperaturer." << endl;
    for (size_t i = 0; i < length; i++) {
        cout << "Temperatur nr " << i + 1 << ": ";
        cin >> user_input;
        if (user_input < 10) {
            ++under_10;
        } else if (user_input > 20) {
            ++over_20;
        } else {
            ++between_10_and_20;
        }
    }
    cout << "Antall under 10 er " << under_10 << endl;
    cout << "Antall mellom 10 og 20 er " << between_10_and_20 << endl;
    cout << "Antall over 20 er " << over_20 << endl;
    return 0;
}

int oppgaveB() {
    int under_10 = 0;
    int between_10_and_20 = 0;
    int over_20 = 0;
    int length = 20;
    double temperatures[length];
    read_temperatures(temperatures, length);
    for (int i = 0; i < length; i++) {
        if (isnormal(temperatures[i])) {
            cout << "Temperatur nr " << i + 1 << ": " << temperatures[i] << endl;
            if (temperatures[i] < 10) {
                ++under_10;
            } else if (temperatures[i] > 20) {
                ++over_20;
            } else {
                ++between_10_and_20;
            }
        }
    }
    cout << "Antall under 10 er " << under_10 << endl;
    cout << "Antall mellom 10 og 20 er " << between_10_and_20 << endl;
    cout << "Antall over 20 er " << over_20 << endl;
    return 0;
}

int read_temperatures(double temperatures[], int length) {
    const char filename[] = "../temperatur.dat";
    ifstream file;
    file.open(filename);
    if (!file) {
        cout << "Feil ved åpning av innfil." << endl;
        exit(EXIT_FAILURE);
    }

    int number;
    for (int i = 0; i < length; ++i) {
        if (file >> number) {
            temperatures[i] = number;
        }
    }

    file.close();
    return 0;
}