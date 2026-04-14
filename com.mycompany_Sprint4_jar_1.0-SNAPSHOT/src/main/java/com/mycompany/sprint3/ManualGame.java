package com.mycompany.sprint3;

/**
 * Implements a manual solitaire game
 * @author David Boatright
 */
class ManualGame extends Game {

    ManualGame(Board board) {
        super(board);
    }

    @Override
    void playTurn() {
        // Do nothing here
        // GUI handles clicks and calls board.makeMove()
    }
}
