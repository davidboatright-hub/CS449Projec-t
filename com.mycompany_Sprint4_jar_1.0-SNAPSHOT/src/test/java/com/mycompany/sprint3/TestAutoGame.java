package com.mycompany.sprint3;

import javax.swing.SwingUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for automated game and randomization
 * @author David Boatright
 */
public class TestAutoGame {
    
    public TestAutoGame() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    // Tests if automated move reduces # of pegs
   @Test
    void testAutomatedGameMakesMove() {
        Board board = new Board(7, "English");
        AutomatedGame game = new AutomatedGame(board);

        int initialScore = board.getScore();

        game.playTurn(); // automated move

        int newScore = board.getScore();

        // A valid move removes exactly one peg
        assertTrue(newScore < initialScore, 
            "Automated game should make a move and reduce peg count");
    }
    // Tests if automated game ends correctly
    @Test
    void testAutomatedGameIsOver() {
        Board board = new Board(7, "English");
        int[][] newBoard = new int[][] {
            {-1,-1,0,0,0,-1,-1},
            {-1,-1,0,0,0,-1,-1},
            { 0, 0,1,1,0, 0, 0},
            { 0, 0,0,0,0, 0, 0},
            { 0, 0,0,0,0, 0, 0},
            {-1,-1,0,0,0,-1,-1},
            {-1,-1,0,0,0,-1,-1}
        };
        board.setBoard(newBoard);

        AutomatedGame game = new AutomatedGame(board);

        // Play until no moves remain
        while (!game.isFinished()) {
            game.playTurn();
        }

        assertTrue(game.isFinished(), 
            "Automated game should detect when no moves remain");
    }
    // Tests if at least one peg is changed by randomize
    @Test
    void testRandomizeManualGameBoard() {
        Board board = new Board(7, "English");

        int[][] before = copyBoard(board.getBoard());
        long seed = System.currentTimeMillis(); // or any random long
        board.randomize(seed); // simulate manual "Randomize" button

        int[][] after = board.getBoard();

        // Check at least one cell changed
        boolean changed = false;

        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (before[r][c] != -1 && before[r][c] != after[r][c]) {
                    changed = true;
                }

                // Ensure still valid values
                if (after[r][c] != -1) {
                    assertTrue(after[r][c] == 0 || after[r][c] == 1,
                        "Board should only contain 0, 1, or -1");
                }
            }
        }

        assertTrue(changed, "Randomize should modify at least one cell");
    }
    // Helper for testRandomizeManualGameBoard
    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}
