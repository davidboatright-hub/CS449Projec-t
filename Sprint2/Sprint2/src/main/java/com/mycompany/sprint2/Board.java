package com.mycompany.sprint2;

/**
 * Implements solitaire board and handles play logic 
 * @author David Boatright
 */
class Board {
    private int[][] board;
    private int size;
    
    Board(int inputSize){
        size = inputSize;
        board = new int[size][size];
        
        int center = size / 2;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                // Makes corners invalid, -1
                if (Math.abs(r - center) > 1 && Math.abs(c - center) > 1) {
                    board[r][c] = -1; // invalid corner
                } else {
                    board[r][c] = 1;  // peg
                }
            }
        }

        // center starts empty
        board[center][center] = 0;
    }
    int getValue(int r, int c){
        return board[r][c];
    }
    int getSize(){
        return size;
    }
    int[][] getBoard(){
        return board;
    }
    // updates board with new move, if valid
    Boolean makeMove(int oldC, int oldR, int newC, int newR){
        if(isValidMove(oldC, oldR, newC, newR)){
                board[oldR][oldC] = 0;
                board[(oldR + newR) / 2][(oldC + newC) / 2] = 0;
                board[newR][newC] = 1;
                return true;
            }
        return false; // move failed
    }
    // Checks if move follows rules: 2 spaces away, empty target, jumps a peg
    Boolean isValidMove(int oldC, int oldR, int newC, int newR) {
        // Dest. not empty
        if (board[newR][newC] != 0){
            return false;
        }
        int cDiff = newC - oldC;
        int rDiff = newR - oldR;
        // Go NS
        if (Math.abs(cDiff) == 2 && rDiff == 0){
            if (board[oldR][oldC + (cDiff / 2)] == 1){
                return true;
            }
            else {
                return false;
            }
        }
        // Go EW
        else if (cDiff == 0 && Math.abs(rDiff) == 2) {
            if (board[oldR + (rDiff / 2)][oldC] == 1){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    // Checks if any valid moves remain
    Boolean isGameOver(){
        int[][] moves = {
            {2, 0},
            {-2, 0},
            {0, 2},
            {0, -2}
        };
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                if (board[r][c] == 1){
                    for(int[] m: moves){

                        int er = r + m[0];
                        int ec = c + m[1];


                        if(isInside(er,ec) && isValidMove(c, r, ec, er)){
                            return false; // found a legal move
                        }
                    }
                }
            }
        }
        return true; // No peg has legal move
    }
    // Helper, checks if index valid for board
    private boolean isInside(int r, int c){
        return r >= 0 &&
               r < size &&
               c >= 0 &&
               c < size;
    }
    // Counts # of pegs remaining
    int getScore() {
        int score = 0;
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                if (board[r][c] == 1){
                    score++;
                }
            }
        }
        return score;
    }
    // Creates a 7x7 board with only one move remaining for testing.
    void cheat() {
        board = new int[][] {
            {-1,-1,0,0,0,-1,-1},
            {-1,-1,0,0,0,-1,-1},
            { 0, 0,1,1,0, 0, 0},
            { 0, 0,0,0,0, 0, 0},
            { 0, 0,0,0,0, 0, 0},
            {-1,-1,0,0,0,-1,-1},
            {-1,-1,0,0,0,-1,-1}
        };
    }
}
