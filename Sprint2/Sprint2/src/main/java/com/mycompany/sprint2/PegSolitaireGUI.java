package com.mycompany.sprint2;

/**
 * Implements solitaire GUI, handles user interaction
 * @author David Boatright
 */
import javax.swing.*;
import java.awt.*;

class PegSolitaireGUI extends JFrame {
    Board gameBoard;
    JButton[][] buttons;
    JPanel boardPanel;
    JLabel statusLabel;
    JButton newGameButton;
    // board is square, use for row and col
    int size = 7; //This will be selected by user eventually
    // Current highlighted peg
    int selectedRow = -1;
    int selectedCol = -1;
    Boolean isOver = false;
    
    public PegSolitaireGUI() {

        gameBoard = new Board(size);

        setTitle("Peg Solitaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Game in progress...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        add(newGameButton, BorderLayout.EAST);

        createBoardGUI();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Creates a new game, clears current state
    void startNewGame() {
        gameBoard = new Board(size);

        boardPanel.removeAll();

        createBoardGUI();

        boardPanel.revalidate();
        boardPanel.repaint();
        
        statusLabel.setText("Game in progress...");
        
        selectedRow = -1;
        selectedCol = -1;

        isOver = false;
    }
    // Translate array board into GUI board
    void createBoardGUI() {

        boardPanel.setLayout(new GridLayout(size, size));
        buttons = new JButton[size][size];

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){

                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(60,60));
                buttons[r][c] = btn;
                // If corner square
                if(gameBoard.getValue(r, c) == -1){
                    btn.setEnabled(false);
                    btn.setVisible(false);
                }

                int row = r;
                int col = c;

                btn.addActionListener(e -> handleClick(row,col));

                boardPanel.add(btn);
            }
        }

        updateBoard();
    }
    // Does peg selection or move if peg already selected
    void handleClick(int r, int c){
        //Invalid square
        if(gameBoard.getValue(r, c) == -1)
            return;

        // Select peg and none already selected
        if(selectedRow == -1 && gameBoard.getValue(r, c) == 1){
            selectedRow = r;
            selectedCol = c;
            buttons[r][c].setBackground(Color.YELLOW);
            return;
        }

        // Attempt move
        if(selectedRow != -1){
            Boolean moveMade = gameBoard.makeMove(selectedCol, selectedRow, c, r);

            buttons[selectedRow][selectedCol].setBackground(null);
            selectedRow = -1;
            selectedCol = -1;
            // Redraw board, check for gameover
            if (moveMade){
                updateBoard();
                isOver = gameBoard.isGameOver();
            }
            if (isOver)
                endGame();
        }
    }
    // Redraws board
    void updateBoard(){

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){

                if(gameBoard.getValue(r, c) == 1)
                    buttons[r][c].setText("●");
                else
                    buttons[r][c].setText("");

            }
        }
    }
    // Locks board, sets game over message
    void endGame(){
        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){
                buttons[r][c].setEnabled(false);
            }
        }
        statusLabel.setText("Game Over! Pegs Remaining: " 
                + gameBoard.getScore());
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new PegSolitaireGUI();
        });
    }
}
