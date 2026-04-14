package com.mycompany.sprint3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import javax.swing.*;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
/**
 * Tests solitaire functionality for manual games
 * @author David Boatright
 */
public class TestGameLogic {
    
    // Testing a 7x7 english board.
    @Test
    public void test7EngBoard() {
        Board board = new Board(7, "English");
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

        Board board = new Board(7, "English");

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

        Board board = new Board(7, "English");

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

        Board board = new Board(7, "English");

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
            int[][] newBoard = new int[][] {
                {-1,-1,0,0,0,-1,-1},
                {-1,-1,0,0,0,-1,-1},
                { 0, 0,1,1,0, 0, 0},
                { 0, 0,0,0,0, 0, 0},
                { 0, 0,0,0,0, 0, 0},
                {-1,-1,0,0,0,-1,-1},
                {-1,-1,0,0,0,-1,-1}
            };
            gui.game.board.setBoard(newBoard);

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
            int[][] newBoard = new int[][] {
                {-1,-1,0,0,0,-1,-1},
                {-1,-1,0,0,0,-1,-1},
                { 0, 0,1,1,0, 0, 0},
                { 0, 0,0,0,0, 0, 0},
                { 0, 0,0,0,0, 0, 0},
                {-1,-1,0,0,0,-1,-1},
                {-1,-1,0,0,0,-1,-1}
            };
            gui.game.board.setBoard(newBoard);

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

    //Tests size dropdown
    @Test
    void testSizeDropdown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            List<String> dropdownItems = new ArrayList<>();
            List<String> testList = new ArrayList<>();

            for (int i = 0; i < gui.sizeDropdown.getItemCount(); i++) {
                dropdownItems.add(gui.sizeDropdown.getItemAt(i));
            }
            testList.add("5");
            testList.add("7");
            testList.add("9");
            assertEquals(dropdownItems.size(), testList.size());
            for (int i = 0; i < gui.sizeDropdown.getItemCount(); i++) {
                assertEquals(dropdownItems.get(i), testList.get(i));
            }
            assertEquals(gui.sizeLabel.getText(), "Size");
        });
    }
    //Tests default size of 7
    @Test
    void testDefaultSize() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            assertEquals(gui.sizeDropdown.getSelectedItem(), "7");
        });
    }
    //Tests selection of size 9
    @Test
    void testSelectSize() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            gui.sizeDropdown.setSelectedItem("9");
            assertEquals(gui.size, 9);
        });
    }
    //Tests type dropdown
    @Test
    void testTypeDropdown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            List<String> dropdownItems = new ArrayList<>();
            List<String> testList = new ArrayList<>();

            for (int i = 0; i < gui.typeDropdown.getItemCount(); i++) {
                dropdownItems.add(gui.typeDropdown.getItemAt(i));
            }
            testList.add("English");
            testList.add("Diamond");
            testList.add("Hex");
            assertEquals(dropdownItems.size(), testList.size());
            for (int i = 0; i < gui.typeDropdown.getItemCount(); i++) {
                assertEquals(dropdownItems.get(i), testList.get(i));
            }
            assertEquals(gui.typeLabel.getText(), "Board Type");
        });
    }
    //Tests default type of english
    @Test
    void testDefaultType() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            assertEquals(gui.typeDropdown.getSelectedItem(), "English");
        });
    }
    //Tests selection of type Hex
    @Test
    void testSelectType() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            gui.typeDropdown.setSelectedItem("Hex");
            assertEquals(gui.boardType, "Hex");
        });
    }
    //Tests mode dropdown
    @Test
    void testModeDropdown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            List<String> dropdownItems = new ArrayList<>();
            List<String> testList = new ArrayList<>();

            for (int i = 0; i < gui.modeDropdown.getItemCount(); i++) {
                dropdownItems.add(gui.modeDropdown.getItemAt(i));
            }
            testList.add("Manual");
            testList.add("Automated");
            assertEquals(dropdownItems.size(), testList.size());
            for (int i = 0; i < gui.modeDropdown.getItemCount(); i++) {
                assertEquals(dropdownItems.get(i), testList.get(i));
            }
            assertEquals(gui.gameModeLabel.getText(), "Game Mode");
        });
    }
    //Tests default mode of manual
    @Test
    void testDefaultMode() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            assertEquals(gui.modeDropdown.getSelectedItem(), "Manual");
        });
    }
    //Tests selection of mode Automated
    @Test
    void testSelectMode() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            PegSolitaireGUI gui = new PegSolitaireGUI();
            gui.modeDropdown.setSelectedItem("Automated");
            assertEquals(gui.gameMode, "Automated");
        });
    }
}
