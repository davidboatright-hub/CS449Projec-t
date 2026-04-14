package com.mycompany.sprint3;

class Move {
    int oldRow, oldCol, newRow, newCol;

    Move(int oldRow, int oldCol, int newRow, int newCol) {
        this.oldRow = oldRow;
        this.oldCol = oldCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    @Override
    public String toString() {
        return oldRow + "," + oldCol + " -> " + newRow + "," + newCol;
    }
}
