package com.mycompany.sprint3;
/**
 * Implements solitaire GUI, handles user interaction
 * @author David Boatright
 */
import javax.swing.*;
import java.awt.*;

class PegSolitaireGUI extends JFrame {
    //Board gameBoard;
    Game game;
    JButton[][] buttons;
    JPanel boardPanel;
    JPanel rightPanel;
    JLabel statusLabel;
    JLabel sizeLabel;
    JLabel typeLabel;
    JLabel gameModeLabel;
    JButton newGameButton;
    JButton randomButton;
    JCheckBox recordCheckBox;
    JButton replayButton;
    JComboBox<String> sizeDropdown;
    JComboBox<String> typeDropdown;
    JComboBox<String> modeDropdown;
    // board is square, use for row and col
    int size = 7;
    String boardType = "English";
    String gameMode = "Manual";
    // Current highlighted peg
    int selectedRow = -1;
    int selectedCol = -1;
    Boolean isOver = false;
    
    public PegSolitaireGUI() {
        
        Board newBoard = new Board(size, boardType);
        game = new ManualGame(newBoard);


        setTitle("Peg Solitaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== CENTER BOARD =====
        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        // ===== STATUS =====
        statusLabel = new JLabel("Game in progress...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // ===== RIGHT PANEL (NEW) =====
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Push everything toward bottom
        rightPanel.add(Box.createVerticalGlue());
        
        // ===== RECORD CHECKBOX =====
        recordCheckBox = new JCheckBox("Record?");
        recordCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(recordCheckBox);
        rightPanel.add(Box.createVerticalStrut(10));
        recordCheckBox.setMaximumSize(new Dimension(120, 25));

        // ===== REPLAY BUTTON =====
        replayButton = new JButton("Replay");

        // Make button same size style
        replayButton.setPreferredSize(new Dimension(120, 30));
        replayButton.setMaximumSize(new Dimension(120, 30));
        replayButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // add action later
        replayButton.addActionListener(e -> handleReplay());

        rightPanel.add(replayButton);
        rightPanel.add(Box.createVerticalStrut(20));

        // ===== SIZE DROPDOWN =====
        sizeLabel = new JLabel("Size");
        String[] sizes = {"5", "7", "9"};
        sizeDropdown = new JComboBox<>(sizes);
        // Set default selection
        sizeDropdown.setSelectedItem("7");
        sizeDropdown.setMaximumSize(new Dimension(120, 25));

        rightPanel.add(sizeLabel);
        rightPanel.add(sizeDropdown);
        rightPanel.add(Box.createVerticalStrut(10));
        
        sizeDropdown.addActionListener(e -> {
            size = Integer.parseInt((String) sizeDropdown.getSelectedItem());
        });

        // ===== BOARD TYPE DROPDOWN =====
        typeLabel = new JLabel("Board Type");
        String[] types = {"English", "Diamond", "Hex"};
        typeDropdown = new JComboBox<>(types);
        typeDropdown.setMaximumSize(new Dimension(120, 25));

        rightPanel.add(typeLabel);
        rightPanel.add(typeDropdown);
        rightPanel.add(Box.createVerticalStrut(20));
        
        typeDropdown.addActionListener(e -> {
            boardType = (String) typeDropdown.getSelectedItem();
         });
        
        // ===== GAME MODE DROPDOWN =====
        gameModeLabel = new JLabel("Game Mode");
        String[] mode = {"Manual", "Automated"};
        modeDropdown = new JComboBox<>(mode);
        modeDropdown.setMaximumSize(new Dimension(120, 25));

        rightPanel.add(gameModeLabel);
        rightPanel.add(modeDropdown);
        rightPanel.add(Box.createVerticalStrut(20));
        
        modeDropdown.addActionListener(e -> {
            gameMode = (String) modeDropdown.getSelectedItem();
         });

        // ===== NEW GAME BUTTON =====
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        // Make button smaller
        newGameButton.setPreferredSize(new Dimension(120, 30));
        newGameButton.setMaximumSize(new Dimension(120, 30));

        // Align to right
        newGameButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(newGameButton);
        
        // ===== RANDOM BUTTON =====
        randomButton = new JButton("Randomize");
        randomButton.addActionListener(e -> handleRandom());

        // Make button smaller
        randomButton.setPreferredSize(new Dimension(120, 30));
        randomButton.setMaximumSize(new Dimension(120, 30));

        // Align to right
        randomButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(randomButton);

        // Add right panel to frame
        add(rightPanel, BorderLayout.EAST);

        createBoardGUI();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Creates a new game, clears current state
    void startNewGame() {
        Board newBoard = new Board(size, boardType);

        if (gameMode.equals("Manual")) {
            game = new ManualGame(newBoard);
        } 
        else {
            game = new AutomatedGame(newBoard);
        }

        boardPanel.removeAll();
        createBoardGUI();
        boardPanel.revalidate();
        boardPanel.repaint();

        statusLabel.setText("Game in progress...");
        selectedRow = -1;
        selectedCol = -1;
        isOver = false;

        // If automatic → start playing
        if (game instanceof AutomatedGame) {
            runAutoGame();
        }
    }
    // Handles automated game inputs
    void runAutoGame() {
        new Thread(() -> {
            while (!game.isFinished()) {
                game.playTurn();

                SwingUtilities.invokeLater(() -> updateBoard());

                try {
                    Thread.sleep(300); // animation delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            SwingUtilities.invokeLater(() -> endGame());
            isOver = true;
        }).start();
    }
    // Handles automated game inputs
    void handleReplay() {
        game = new ReplayGame();
        size = game.getSize();
        boardPanel.removeAll();
        createBoardGUI();
        boardPanel.revalidate();
        boardPanel.repaint();

        statusLabel.setText("Game in progress...");
        selectedRow = -1;
        selectedCol = -1;
        isOver = false;
        new Thread(() -> {
            while (!game.isFinished()) {
                game.playTurn();

                SwingUtilities.invokeLater(() -> updateBoard());

                try {
                    Thread.sleep(300); // animation delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            recordCheckBox.setSelected(false);
            SwingUtilities.invokeLater(() -> endGame());
            isOver = true;
        }).start();
        size = Integer.parseInt((String) sizeDropdown.getSelectedItem());
        boardType = (String) typeDropdown.getSelectedItem();
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
                if(game.isInvalidCell(r, c)){
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
        if(game.isInvalidCell(r, c))
            return;

        // Select peg and none already selected
        if(selectedRow == -1 && game.isPeg(r, c)){
            selectedRow = r;
            selectedCol = c;
            buttons[r][c].setBackground(Color.YELLOW);
            return;
        }

        // Attempt move
        if(selectedRow != -1){
            Boolean moveMade = game.makeMove(selectedCol, selectedRow, c, r);

            buttons[selectedRow][selectedCol].setBackground(null);
            selectedRow = -1;
            selectedCol = -1;
            // Redraw board, check for gameover
            if (moveMade){
                updateBoard();
                isOver = game.isFinished();
            }
            if (isOver)
                endGame();
        }
    }
    // Handles randomize button press
    void handleRandom() {
        if (!isOver){
            long seed = System.currentTimeMillis();
            game.randomize(seed);
            updateBoard();
            isOver = game.isFinished();
            if (isOver)
                endGame();
        }
    }
    // Redraws board
    void updateBoard(){

        for(int r = 0; r < game.getSize(); r++){
            for(int c = 0; c < game.getSize(); c++){

                if(game.isPeg(r, c))
                    buttons[r][c].setText("●");
                else
                    buttons[r][c].setText("");

            }
        }
    }
    // Locks board, sets game over message
    void endGame(){
        for(int r = 0; r < game.getSize(); r++){
            for(int c = 0; c < game.getSize(); c++){
                buttons[r][c].setEnabled(false);
            }
        }
        statusLabel.setText("Game Over! Pegs Remaining: " 
                + game.getScore());
        if (recordCheckBox.isSelected()) {
            game.outputData();
        }
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new PegSolitaireGUI();
        });
    }
}

