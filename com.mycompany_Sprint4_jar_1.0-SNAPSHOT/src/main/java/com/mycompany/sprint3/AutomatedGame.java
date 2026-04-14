package com.mycompany.sprint3;

/**
 * Implements an automated solitaire game
 * @author David Boatright
 */
class AutomatedGame extends Game {

    AutomatedGame(Board board) {
        super(board);
    }

    @Override
    void playTurn() {
        // Very simple: find first valid move
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {

                if (board.getValue(r, c) == 1) {

                    int[][] moves = {
                        {2,0}, {-2,0}, {0,2}, {0,-2}
                    };

                    for (int[] m : moves) {
                        int newR = r + m[0];
                        int newC = c + m[1];

                        if (newR >= 0 && newR < board.getSize() &&
                            newC >= 0 && newC < board.getSize()) {
                            Boolean madeMove = board.makeMove(c, r, newC, newR);
                            if (madeMove) {
                                // log move
                                moveHistory.add(new Move(r, c, newR, newC));
                                return; // make ONE move per turn
                            }
                        }
                    }
                }
            }
        }
    }
}
