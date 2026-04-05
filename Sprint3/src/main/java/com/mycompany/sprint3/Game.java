package com.mycompany.sprint3;

/**
 * Parent class that handles a game of solitaire
 * @author David Boatright
 */
abstract class Game {
    protected Board board;

    Game(Board board) {
        this.board = board;
    }
    Board getBoard() {
        return board;
    }
    boolean isPeg(int r, int c){
        return (board.getValue(r, c) == 1);
    }
    boolean isEmpty(int r, int c) {
        return (board.getValue(r, c) == 0);
    }
    boolean isInvalidCell(int r, int c) {
        return (board.getValue(r, c) == -1);
    }
    int getSize() {
        return board.getSize();
    }
    int getScore(){
        return board.getScore();
    }
    void randomize(){
        board.randomize();
    }
    boolean isFinished() {
        return board.isGameOver();
    }
    Boolean makeMove(int oldC, int oldR, int newC, int newR) {
        return board.makeMove(oldC, oldR, newC, newR);
    }

    // Defines how moves are made
    abstract void playTurn();
}
