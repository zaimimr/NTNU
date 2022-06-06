#include <iostream>
#include <vector>
#include <functional>

using namespace std;


class Chess {
public:
    enum class Player {
        white, black
    };

    static string player_name(Player player) {
        if (player == Player::white)
            return "white";
        else
            return "black";
    }

    vector<function<void(Player winner)>> on_victory;

    /// In this game, black always wins
    void play() {
        auto winner = Chess::Player::black;
        for (auto &f : on_victory)
            f(winner);
    }
};

int main() {
    Chess chess;
    chess.on_victory.emplace_back([](Chess::Player winner) {
        cout << Chess::player_name(winner) << " won" << endl;
    });
    chess.on_victory.emplace_back([](Chess::Player winner_color) {
        Chess::Player looser;
        if (winner_color == Chess::Player::white)
            looser = Chess::Player::black;
        else
            looser = Chess::Player::white;
        cout << Chess::player_name(looser) << " lost" << endl;
    });
    chess.play();
}