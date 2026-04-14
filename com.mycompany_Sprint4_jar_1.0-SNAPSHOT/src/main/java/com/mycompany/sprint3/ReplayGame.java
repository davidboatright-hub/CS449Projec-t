package com.mycompany.sprint3;

import java.util.ArrayList;

class ReplayGame extends Game {
    private ArrayList<Move> moves;
    private int currentIndex = 0;

    ReplayGame() {
        //super(board);
        //this.moves = moves;
        String boardType = "English";
        int boardSize = 7;
        String[] boardData = MoveIO.getBoardData("replays/BoardData.txt");
        boardType = boardData[0];
        boardSize = Integer.parseInt(boardData[1]);
        super(new Board(boardSize, boardType));
        this.moves = MoveIO.loadFromFile("replays/BoardData.txt");
    }

    @Override
    void playTurn() {
        if (currentIndex < moves.size()) {
            Move m = moves.get(currentIndex++);
            if (m instanceof RandomizeMove) {
                RandomizeMove rm = (RandomizeMove) m;
                board.randomize(rm.seed);
            }
            else {
                board.makeMove(m.oldCol, m.oldRow, m.newCol, m.newRow);
            }
        }
    }

    @Override
    boolean isFinished() {
        return currentIndex >= moves.size();
    }
}
