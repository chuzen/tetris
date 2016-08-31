
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is the view for the Tetris game. 
 * It creates the user interface and updates the game display based on messages
 * from the controller and queue objects from the model.
 * 
 * @author John Cowgill, Baldwin Browne
 * @version November 16, 2014
 */
public class TetrisView {
    
    private static final int BLOCK_SIZE = 20;
    private static final int GRID_WIDTH = Tetris.TETRIS_WIDTH;
    private static final int GRID_HEIGHT = Tetris.TETRIS_HEIGHT + 4;
    private static final int GRID_SCALE = BLOCK_SIZE * GRID_HEIGHT / 4;
    private static final Color TETRIS_BACKGROUND_COLOR = Color.BLACK;
    private static final Color TETRIMINO_OUTLINE_COLOR = Color.GRAY;
    private static final Color EMPTY_BLOCK_OUTLINE_COLOR = Color.DARK_GRAY;
    private static final Color HIGHLIGHTED_BLOCK_COLOR = Color.WHITE;
    private static final Color HIGHLIGHTED_BLOCK_OUTLINE_COLOR = Color.BLACK;
    
    private TetrisController controller;
    private BlockGridPanel holdGrid;
    private BlockGridPanel nextGrid;
    private BlockGridPanel tetrisGrid;
    private JFrame viewFrame;
    private JLabel levelValueLabel;
    private JLabel linesValueLabel;
    private JLabel highScoreValueLabel;
    private JLabel scoreValueLabel;
    private JButton quitButton;
    private JButton pauseButton;
    private JButton startButton;

    /**
     * Constructor builds the user interface for the Tetris game.
     * @param controller the TetrisController that created this TetrisViww
     */
    public TetrisView(final TetrisController controller) {
        this.controller = controller;
        
        viewFrame = new JFrame("Tetris");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setLayout(new BorderLayout());
        
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());
        
        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
        
        JPanel holdGridContainer = new JPanel();
        holdGridContainer.setLayout(new BorderLayout());
        holdGridContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE));
        JLabel holdLabel = new JLabel("Hold Piece");
        holdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        holdGridContainer.add(holdLabel, BorderLayout.CENTER);
        holdGrid = new BlockGridPanel(4, 4, BLOCK_SIZE);
        holdGridContainer.add(holdGrid, BorderLayout.SOUTH);
        leftSide.add(holdGridContainer);
        
        JPanel leftPadding = new JPanel();
        leftPadding.setMaximumSize(new Dimension(GRID_SCALE, 3 * GRID_SCALE));
        leftSide.add(leftPadding);
        
        JPanel levelContainer = new JPanel();
        levelContainer.setLayout(new BorderLayout());
        levelContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE / 2));
        JLabel levelLabel = new JLabel("Level");
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelContainer.add(levelLabel, BorderLayout.CENTER);
        levelValueLabel = new JLabel("0");
        levelValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelContainer.add(levelValueLabel, BorderLayout.SOUTH);
        leftSide.add(levelContainer);
        
        JPanel linesContainer = new JPanel();
        linesContainer.setLayout(new BorderLayout());
        linesContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE / 2));
        JLabel linesLabel = new JLabel("Lines");
        linesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesContainer.add(linesLabel, BorderLayout.CENTER);
        linesValueLabel = new JLabel("0");
        linesValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesContainer.add(linesValueLabel, BorderLayout.SOUTH);
        leftSide.add(linesContainer);
        
        viewPanel.add(leftSide, BorderLayout.WEST);
        
        JPanel tetrisGridContainer = new JPanel();
        tetrisGrid = new BlockGridPanel(GRID_HEIGHT, GRID_WIDTH, BLOCK_SIZE);
        tetrisGrid.setBackground(TETRIS_BACKGROUND_COLOR);
        tetrisGridContainer.add(tetrisGrid);
        viewPanel.add(tetrisGridContainer, BorderLayout.CENTER);
        
        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        
        JPanel nextGridContainer = new JPanel();
        nextGridContainer.setLayout(new BorderLayout());
        nextGridContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE));
        JLabel nextLabel = new JLabel("Next Piece");
        nextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nextGridContainer.add(nextLabel, BorderLayout.CENTER);
        nextGrid = new BlockGridPanel(4, 4, BLOCK_SIZE);
        nextGridContainer.add(nextGrid, BorderLayout.SOUTH);
        rightSide.add(nextGridContainer);
        
        JPanel rightPadding = new JPanel();
        rightPadding.setMaximumSize(new Dimension(GRID_SCALE, 3 * GRID_SCALE));
        rightSide.add(rightPadding);
        
        JPanel highScoreContainer = new JPanel();
        highScoreContainer.setLayout(new BorderLayout());
        highScoreContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE / 2));
        JLabel highScoreLabel = new JLabel("High Score");
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreContainer.add(highScoreLabel, BorderLayout.CENTER);
        highScoreValueLabel = new JLabel("0");
        highScoreValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreContainer.add(highScoreValueLabel, BorderLayout.SOUTH);
        rightSide.add(highScoreContainer);
        
        JPanel scoreContainer = new JPanel();
        scoreContainer.setLayout(new BorderLayout());
        scoreContainer.setMaximumSize(new Dimension(GRID_SCALE, GRID_SCALE / 2));
        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreContainer.add(scoreLabel, BorderLayout.CENTER);
        scoreValueLabel = new JLabel("0");
        scoreValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreContainer.add(scoreValueLabel, BorderLayout.SOUTH);
        rightSide.add(scoreContainer);
        
        viewPanel.add(rightSide, BorderLayout.EAST);
        
        JPanel controlPanel = new JPanel();
        
        quitButton = new JButton("Quit");
        quitButton.setFocusable(false);
        quitButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.processInput(KeyEvent.VK_ESCAPE);
            }
        
        });
        controlPanel.add(quitButton);
        
        pauseButton = new JButton("Pause");
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.processInput('P');
            }
        
        });
        controlPanel.add(pauseButton);
        
        startButton = new JButton("Start");
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.processInput(KeyEvent.VK_F10);
            }
        
        });
        controlPanel.add(startButton);

        viewFrame.add(viewPanel, BorderLayout.CENTER);
        viewFrame.add(controlPanel, BorderLayout.SOUTH);
        viewFrame.pack();
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);

        viewFrame.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                controller.processInput(e.getKeyCode());
            }
        
        });
        viewFrame.setFocusable(true);
        
        resetView();
        displayHelp();
    }
    
    /**
     * Clears the hold piece, next piece, and Tetris well grids of all blocks.
     */
    public void resetView() {
        holdGrid.clearBlockGrid(null, null);
        nextGrid.clearBlockGrid(null, null);
        tetrisGrid.clearBlockGrid(null, EMPTY_BLOCK_OUTLINE_COLOR);
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < GRID_WIDTH; c++) {
                tetrisGrid.paintBlock(r, c, null, null);
            }
        }
    }
    
    /**
     * Used by the TetrisController to update the display of the game level.
     * @param level the current game level
     */
    public void setLevelValue(int level) {
        levelValueLabel.setText(Integer.toString(level));
    }

    /**
     * Used by the TetrisController to update the display of the lines cleared.
     * @param lines the current number of lines cleared
     */
    public void setLinesValue(int lines) {
        linesValueLabel.setText(Integer.toString(lines));
    }

    /**
     * Used by the TetrisController to update the display of the high score.
     * @param highScore the current high score
     */
    public void setHighScoreValue(int highScore) {
        highScoreValueLabel.setText(Integer.toString(highScore));
    }
    
    /**
     * Used by the TetrisController to update the display of the current score.
     * @param score the current score
     */
    public void setScoreValue(int score) {
        scoreValueLabel.setText(Integer.toString(score));
    }
    
    /**
     * Used by the TetrisController to enable or disable the quit button.
     * @param state true to enable, false to disable
     */
    public void setQuitButton(boolean state) {
        quitButton.setEnabled(state);
    }
    
    /**
     * Used by the TetrisController to enable or disable the pause button.
     * @param state true to enable, false to disable
     */
    public void setPauseButton(boolean state) {
        pauseButton.setEnabled(state);
    }
    
    /**
     * Used by the TetrisController to enable or disable the start button.
     * @param state true to enable, false to disable
     */
    public void setStartButton(boolean state) {
        startButton.setEnabled(state);
    }
    
    /**
     * Used by the TetrisController to change the text of the pause button.
     * @param paused true if paused, false if not paused
     */
    public void togglePauseButtonText(boolean paused) {
        if(paused) pauseButton.setText("Resume");
        else pauseButton.setText("Pause");
    }
    
    /**
     * Takes a character symbol representing a Tetrimino shape and returns the
     * appropriate color for that particular shape. Uppercase letters are for
     * regular Tetriminos and lowercase letters are for ghost images.
     * @param symbol a character representing the shape
     * @return the Color to use for displaying the shape
     */
    private Color getSymbolColor(char symbol) {
        switch(symbol) {
        // Regular piece colors
            case 'I': return Color.CYAN;
            case 'J': return Color.BLUE;
            case 'L': return Color.ORANGE;
            case 'O': return Color.YELLOW;
            case 'S': return Color.GREEN;
            case 'T': return Color.MAGENTA;
            case 'Z': return Color.RED;
        // Ghost piece colors
            case 'i': return new Color(0.0f, 1.0f, 1.0f, 0.25f); //Color.CYAN;
            case 'j': return new Color(0.0f, 0.0f, 1.0f, 0.35f); //Color.BLUE;
            case 'l': return new Color(1.0f, 0.6f, 0.0f, 0.25f); //Color.ORANGE;
            case 'o': return new Color(1.0f, 1.0f, 0.0f, 0.25f); //Color.YELLOW;
            case 's': return new Color(0.0f, 1.0f, 0.0f, 0.25f); //Color.GREEN;
            case 't': return new Color(1.0f, 0.0f, 1.0f, 0.25f); //Color.MAGENTA;
            case 'z': return new Color(1.0f, 0.0f, 0.0f, 0.25f); //Color.RED;
        // Empty piece color
            default: return null;
        }
    }
    
    /**
     * Displays the given Tetrimino shape on the specified BlockGridPanel.
     * Used for updating the hold piece and next piece displays.
     * @param t the Tetrimino to display
     * @param grid the BlockGridPanel where the Tetrimino should be displayed
     */
    private void drawPieceOnGrid(Tetrimino t, BlockGridPanel grid) {
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(t.hasCoordinates(r, c)) {
                    grid.paintBlock(r, c, getSymbolColor(t.getSymbol()),
                    TETRIMINO_OUTLINE_COLOR);
                } else {
                    grid.paintBlock(r, c, null, null);
                }
            }
        }
    }
        
    /**
     * This method is called by the TetrisController's Timer, letting the
     * TetrisView know when it is time to check the Queue for messages from
     * the TetrisModel about blocks that need to be changed on the display.
     */
    public void update() {
        List blockList = new ArrayList<ModifyBlock>();
        ClearLines cl = null;
        HighlightLines hl = null;
        // Process all of the messages in the TetrisController's Queue
        while(controller.queueHasNext()) {
            Object queueObject = controller.queueRemove();
            if(queueObject instanceof HoldTetrimino) {
                // Update the hold piece
                drawPieceOnGrid(((HoldTetrimino) queueObject).hold, holdGrid);
            }
            if(queueObject instanceof NextTetrimino) {
                // Update the next piece
                drawPieceOnGrid(((NextTetrimino) queueObject).next, nextGrid);
            }
            if(queueObject instanceof GhostTetrimino) {
                // Update the ghost image
                GhostTetrimino gt = (GhostTetrimino) queueObject;
                for(int i = 0; i < 4; i++) {
                    ModifyBlock mb =
                        new ModifyBlock(gt.row + gt.coords[i][0], gt.column + gt.coords[i][1],
                        getSymbolColor(gt.symbol), EMPTY_BLOCK_OUTLINE_COLOR);
                    blockList.add(mb);
                }
            }
            if(queueObject instanceof DisplayTetrimino) {
                // Update the current Tetrimino
                DisplayTetrimino dt = (DisplayTetrimino) queueObject;
                for(int i = 0; i < 4; i++) {
                    ModifyBlock mb =
                        new ModifyBlock(dt.row + dt.coords[i][0], dt.column + dt.coords[i][1],
                        getSymbolColor(dt.symbol), TETRIMINO_OUTLINE_COLOR);
                    blockList.add(mb);
                }
            }
            if(queueObject instanceof EraseTetrimino) {
                // Erase a Tetrimino that has moved
                EraseTetrimino et = (EraseTetrimino) queueObject;
                for(int i = 0; i < 4; i++) {
                    ModifyBlock mb =
                        new ModifyBlock(et.row + et.coords[i][0], et.column + et.coords[i][1],
                        null, ((et.row + et.coords[i][0]) < 4) ? null : EMPTY_BLOCK_OUTLINE_COLOR);
                    blockList.add(mb);
                }
            }
            if(queueObject instanceof HighlightLines) {
                // Save for now, process after the Queue is empty
                hl = (HighlightLines) queueObject;
            }
            if(queueObject instanceof ClearLines) {
                // Save for now, process after the Queue is empty
                cl = (ClearLines) queueObject;
            }
        }
        /*
            If there are no lines to clear, take care of the block list now.
            If there are lines to clear, clear those lines first and then take
            care of the block list afterward.
        */
        if(cl == null) tetrisGrid.paintBlockList(blockList);
        else {
            for(int r = 3; r >= 0; r--) {
                if(cl.clear_line[r]) {
                    tetrisGrid.removeRow(cl.first_line + r);
                }
            }
            for(int i = 0; i < cl.line_count; i++) {
                tetrisGrid.addNewRow(4, null, EMPTY_BLOCK_OUTLINE_COLOR);
            }
            tetrisGrid.paintBlockList(blockList);
            // play sound effect
            if (cl.line_count <= 3) {
            	controller.playCL();
            } else {
            	controller.playTetris();
            }
        }
        // Highlight the indicated lines (they will be cleared on next update)
        if(hl != null) {
            for(int r = 3; r >= 0; r--) {
                if(hl.highlight_line[r]) {
                    tetrisGrid.removeRow(hl.first_line + r);
                    tetrisGrid.addNewRow(hl.first_line + r,
                        HIGHLIGHTED_BLOCK_COLOR, HIGHLIGHTED_BLOCK_OUTLINE_COLOR);
                }
            }
            tetrisGrid.repaint();
        }
    }
    
    
    /**
     * Inform the user that the game is over.
     */
    public void displayGameOver() {
    	update();
        JOptionPane.showMessageDialog(viewFrame, "Game Over!", "",
            JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Inform the user how to play the game.
     */
    public void displayHelp() {
        JOptionPane.showMessageDialog(viewFrame, "Welcome to Tetris!\n\n" +
            "How to Play:\n" +
            "    Use pieces to form horizontal lines of blocks.\n" +
            "    Forming a line clears the blocks in that line.\n" +
            "    Clear multiple lines to earn more points.\n" +
            "    Keep the blocks from stacking up too high.\n" +
            "    Once per piece you may make an exchange.\n" +
            "    Good luck!\n\n" +
            "Keyboard Commands:\n" +
            "    A or left arrow: Move piece left\n" +
            "    D or right arrow: Move piece right\n" +
            "    S or down arrow: Move piece down\n" +
            "    E: Rotate piece clockwise\n" +
            "    Q: Rotate piece counter-clockwise\n" +
            "    W or up arrow: Exchange hold piece\n" +
            "    Spacebar: Drop piece to the bottom\n" +
            "    P: Pause the game\n" +
            "    ESC: Quit the game\n" +
            "    F10: Start the game\n",
            "Tetris Help", JOptionPane.PLAIN_MESSAGE);
    }
    
}
