package com.mycompany.sprint3;

import java.util.ArrayList;

/**
 * Parent class that handles a game of solitaire
 * @author David Boatright
 */
abstract class Game {
    protected Board board;
    protected ArrayList<Move> moveHistory = new ArrayList<>();

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
    void randomize(long seed){
        board.randomize(seed);
        moveHistory.add(new RandomizeMove(seed));
    }
    boolean isFinished() {
        return board.isGameOver();
    }
    Boolean makeMove(int oldC, int oldR, int newC, int newR) {
        Boolean moveMade = board.makeMove(oldC, oldR, newC, newR);
        if (moveMade){
            moveHistory.add(new Move(oldR, oldC, newR, newC));
        }
        return moveMade;
    }
    void outputData(){
        MoveIO.saveToFile(moveHistory, board.getSize(), board.getType(), "replays/BoardData.txt");
    }
    // Defines how moves are made
    abstract void playTurn();
}
