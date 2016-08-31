

/**
 * This class is the model for the Tetris game. 
 * It creates a simulation and updates the state of the game based on messages
 * from the controller.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class TetrisModel {
    
    private TetrisController controller;
    private TetriminoWell well;
    private TetriminoBag bag;
    private Tetrimino current, held, ghost, held_ghost;
    private ClearLines clear_highlighted;
    private int row, column, ghost_row;
    private boolean exchanged, show_ghost = true;
    private int tetriminos_dropped, single_lines_cleared, double_lines_cleared,
        triple_lines_cleared, tetris_cleared;
    
    /**
     * Constructor keeps track of the controller.
     * @param controller the TetrisController that created this TetrisModel
     */
    public TetrisModel(TetrisController controller) {
        this.controller = controller;
    }
    
    /**
     * Resets the game simulation.
     */
    public void resetModel() {
        tetriminos_dropped = 0;
        single_lines_cleared = 0;
        double_lines_cleared = 0;
        triple_lines_cleared = 0;
        tetris_cleared = 0;
        well = new TetriminoWell();
        bag = new TetriminoBag();
        held = null;
        getNextTetrimino();
    }
    
    /**
     * Resets the position of the current Tetrimino back to the top of the well.
     */
    private void resetPosition() {
        row = 0;
        column = (Tetris.TETRIS_WIDTH - 4) / 2;
        ghost_row = 0;
    }
    
    /**
     * Inserts messages into the TetrisController's Queue telling the TetrisView
     * how to display the current Tetrimino and its ghost image.
     */
    private void displayTetrimino() {
        ghost_row = row;
        while(!well.checkCollision(ghost, ghost_row + 1, column)) { ghost_row++; }
        if(show_ghost) {
            controller.queueInsert(new GhostTetrimino(ghost, ghost_row, column));
        }
        controller.queueInsert(new DisplayTetrimino(current, row, column));
    }
    
    /**
     * Inserts messages into the TetrisController's Queue telling the TetrisView
     * how to erase the current Tetrimino and its ghost image.
     */
    private void eraseTetrimino() {
        if(show_ghost) {
            controller.queueInsert(new EraseTetrimino(ghost, ghost_row, column));
        }
        controller.queueInsert(new EraseTetrimino(current, row, column));
    }
    
    /**
     * First checks to see if the well has overflowed (if so, game over),
     * otherwise picks the next Tetrimino out of the bag, places it at the top
     * of the well, and inserts a message into the TetrisController's Queue
     * telling the TetrisView what the next piece will be.
     */
    private void getNextTetrimino() {
        if(well.overflow()) {
            controller.gameOver();
            return;
        }
        current = bag.pickTetrimino();
        ghost = new Tetrimino(current.getShape());
        resetPosition();
        displayTetrimino();
        controller.queueInsert(new NextTetrimino(bag.nextTetrimino()));
        tetriminos_dropped++;
        exchanged = false;
        clear_highlighted = null;
    }

    /**
     * Try to move the current Tetrimino to the left one block.
     * @return true if the move was successful, false otherwise
     */
    public boolean tryMoveLeft() {
        if(clear_highlighted != null) return false;
        if(well.checkCollision(current, row, column - 1)) return false;
        eraseTetrimino();
        column--;
        displayTetrimino();
        return true;
    }
    
    /**
     * Try to move the current Tetrimino to the right one block.
     * @return true if the move was successful, false otherwise
     */
    public boolean tryMoveRight() {
        if(clear_highlighted != null) return false;
        if(well.checkCollision(current, row, column + 1)) return false;
        eraseTetrimino();
        column++;
        displayTetrimino();
        return true;
    }
    
    /**
     * Try to move the current Tetrimino down one block.
     * @return true if the move was successful, false otherwise
     */
    public boolean tryMoveDown() {
        if(clear_highlighted != null) return false;
        if(well.checkCollision(current, row + 1, column)) return false;
        eraseTetrimino();
        row++;
        displayTetrimino();
        return true;
    }
    
    /**
     * Try to rotate the current Tetrimino clockwise 90 degrees.
     * @return true if the rotate was successful, false otherwise
     */
    public boolean tryRotateClockwise() {
        if(clear_highlighted != null) return false;
        current.rotateClockwise();
        boolean fail = well.checkCollision(current, row, column);
        current.rotateCounterClockwise();
        if(fail) return false;
        eraseTetrimino();
        current.rotateClockwise();
        ghost.rotateClockwise();
        displayTetrimino();
        return true;
    }
    
    /**
     * Try to rotate the current Tetrimino counter-clockwise 90 degrees.
     * @return true if the rotate was successful, false otherwise
     */
    public boolean tryRotateCounterClockwise() {
        if(clear_highlighted != null) return false;
        current.rotateCounterClockwise();
        boolean fail = well.checkCollision(current, row, column);
        current.rotateClockwise();
        if(fail) return false;
        eraseTetrimino();
        current.rotateCounterClockwise();
        ghost.rotateCounterClockwise();
        displayTetrimino();
        return true;
    }
    
    /**
     * Try to exchange the current Tetrimino with the hold piece.
     * @return true if the exchange was successful, false otherwise
     */
    public boolean tryHoldExchange() {
        if(clear_highlighted != null) return false;
        if(exchanged) return false;
        if(held == null) {
            held = current;
            held_ghost = ghost;
            eraseTetrimino();
            getNextTetrimino();
        } else {
            Tetrimino temp = held;
            Tetrimino temp_ghost = held_ghost;
            held = current;
            held_ghost = ghost;
            eraseTetrimino();
            current = temp;
            ghost = temp_ghost;
            resetPosition();
            displayTetrimino();
        }
        exchanged = true;
        controller.queueInsert(new HoldTetrimino(held));
        return true;
    }
    
    /**
     * Drops the current Tetrimino to the bottom of the well.
     */
    public void drop() {
        if(clear_highlighted != null) return;
        eraseTetrimino();
        while(!well.checkCollision(current, row + 1, column)) { row++; }
        displayTetrimino();
        settle();
    }
    
    /**
     * Settles the current Tetrimino within the well and checks to see if any
     * rows were cleared as a result. If any rows were cleared, this method will
     * insert a message in the TetrisController's Queue telling the TetrisView
     * which lines to highlight, and will create a message about which lines to
     * clear to be sent on the next update of the TetrisModel.
     */
    private void settle() {
        int rows_cleared = well.settleTetrimino(current, row, column);
        if(rows_cleared != 0) {
            HighlightLines hl = new HighlightLines(row, rows_cleared);
            ClearLines cl = new ClearLines(row, rows_cleared);
            controller.queueInsert(hl);
            clear_highlighted = cl;
            switch(cl.line_count) {
                case 1:
                    single_lines_cleared++;
                    break;
                case 2:
                    double_lines_cleared++;
                    break;
                case 3:
                    triple_lines_cleared++;
                    break;
                case 4:
                    tetris_cleared++;
                    break;
            }
            controller.timerHiccup(); // Reset the current TICK of the Timer
        } else {
            getNextTetrimino();
        }
    }
    
    /**
     * This method is called by the TetrisController's Timer, letting the
     * TetrisModel know when it is time to advance the simulation by one step.
     */
    public void update() {
        if(clear_highlighted != null) {
            controller.queueInsert(clear_highlighted);
            clear_highlighted = null;
            getNextTetrimino();
            return;
        }
        if(!tryMoveDown()) {
            settle();
        }
        // Update the statistics information
        controller.processStatistics(tetriminos_dropped, single_lines_cleared,
            double_lines_cleared, triple_lines_cleared, tetris_cleared);
    }

}
