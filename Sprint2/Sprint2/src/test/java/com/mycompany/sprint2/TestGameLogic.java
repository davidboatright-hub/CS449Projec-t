package com.mycompany.sprint2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import javax.swing.*;
import java.awt.Color;

/**
 * Tests solitaire functionality
 * @author David Boatright
 */
public class TestGameLogic {
    
    // Testing a 7x7 english board.
    // This will be extended for more sizes, boards in Sprint3
    @Test
    public void test7EngBoard() {
        Board board = new Board(7);
        // Correct 7x7 english board
        int[][] testBoard = new int[][] {
            {-1,-1,1,1,1,-1,-1},
            {-1,-1,1,1,1,-1,-1},
            { 1, 1,1,1,1, 1, 1},
            { 1, 1,1,0,1, 1, 1},
            { 1, 1,1,1,1, 1, 1},
            {-1,-1,1,1,1,-1,-1},
            {-1,-1,1,1,1,-1,-1}
        };
        assertArrayEquals(board.getBoard(), testBoard);
    }
    // Tests if selecting peg highlights and stores correct info
    @Test
    void selectingPegHighlightsIt() throws Exception {

        SwingUtilities.invokeAndWait(() -> {

            PegSolitaireGUI gui = new PegSolitaireGUI();

            int r = 3;
            int c = 2;
            // Click (3, 2)
            gui.buttons[r][c].doClick();
            // (3,2) is now stored, highlighted yellow
            assertEquals(r, gui.selectedRow);
            assertEquals(c, gui.selectedCol);
            assertEquals(Color.YELLOW, gui.buttons[r][c].getBackground());
        });
    }
    // Tests a valid jump
    @Test
    void testJumpPeg() {

        Board board = new Board(7);

        // Start peg at (3,1)
        // Jumped peg at (3,2)
        // Empty destination at (3,3)

        // make move
        boolean moveMade = board.makeMove(1,3,3,3);

        // Assert move made sucessfully
        assertTrue(moveMade);

        // Start peg removed
        assertEquals(0, board.getValue(3,1));

        // Jumped peg removed
        assertEquals(0, board.getValue(3,2));

        // Peg added to destination
        assertEquals(1, board.getValue(3,3));
    }
    // Test jump w/o a peg in the middle
    @Test
    void testFailJump() {

        Board board = new Board(7);

        // Start peg at (3,1)
        // Jumped peg at (3,2)
        // Empty destination at (3,3)

        // Makes a valid move, tries to move 3,3 back to 3,1, jumping an empty
        // space
        board.makeMove(1,3,3,3);
        boolean moveMade = board.makeMove(3,3,1,3);

        // Move not made
        assertFalse(moveMade);

        // Board state unchanged after first move
        assertEquals(0, board.getValue(3,1));

        
        assertEquals(0, board.getValue(3,2));

       
        assertEquals(1, board.getValue(3,3));
    }
    // Test a jump >2 spaces away
    @Test
    void testLongJump() {

        Board board = new Board(7);

        // Start peg at (3,0)
        // Jumped pegs at (3,1), (3,2)
        // Empty destination at (3,3)

        // Tries to jump 2 pegs
        boolean moveMade = board.makeMove(0,3,3,3);

        // Fails jump
        assertFalse(moveMade);
        
        // All spaces involved unchanged
        assertEquals(1, board.getValue(3,0));
        
       
        assertEquals(1, board.getValue(3,1));

  
        assertEquals(1, board.getValue(3,2));

        
        assertEquals(0, board.getValue(3,3));
    }
    // Tests if game over state recognized
    @Test
    void testEndingGame() {
        // Make board with only one move left
        Board board = new Board(7);
        board.cheat();

        // Makes final move
        board.makeMove(2,2,4,2);
        Boolean isOver = board.isGameOver();

        // Game is over
        assertTrue(isOver);
    }
    //Tests if game over message is displayed correctly
    @Test
    void testGameOverMsg() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Makes board GUI with one valid move remaining
            PegSolitaireGUI gui = new PegSolitaireGUI();
            gui.gameBoard.cheat();

            int r = 2;
            int c = 2;
            // Makes final move on GUI
            gui.buttons[r][c].doClick();
            c = 4;
            gui.buttons[r][c].doClick();
            // Check game over message
            assertEquals(gui.statusLabel.getText(), "Game Over! Pegs Remaining: 1");
        });
    }
    // Tests if game board locked after last move
    @Test
    void testGameOverNoClick() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Makes board GUI with one valid move remaining
            PegSolitaireGUI gui = new PegSolitaireGUI();
            gui.gameBoard.cheat();

            int r = 2;
            int c = 2;
            // Makes final move on GUI
            gui.buttons[r][c].doClick();
            c = 4;
            gui.buttons[r][c].doClick();
            //Try to click on last peg again
            gui.buttons[r][c].doClick();
            // Confirm click not recognized
            assertNotEquals(r, gui.selectedRow);
            assertNotEquals(c, gui.selectedCol);
            assertNotEquals(Color.YELLOW, gui.buttons[r][c].getBackground());
        });
    }
}
