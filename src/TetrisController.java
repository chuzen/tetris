

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.LinkedList;

/**
 * This class is the controller for the Tetris game. 
 * It creates the model and the view, and processes state-changes from the
 * model and user-input from the view.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version November 16, 2014
 */
public class TetrisController {

    private static final int ONE_TICK = 100; // 1000 = 1 second
    private static final int MAX_LEVEL = 10;
    private static final int CLEARS_PER_LEVEL = 4;

    private Queue queue;
    private Timer timer;
    private Toolkit toolkit;
    private TetrisModel model;
    private TetrisView view;
    private TetrisMusic music;
    private TetrisSFX sfx;
    private boolean running;
    private boolean paused;
    private int counter, level, highScore;
    
    /**
     * Constructor sets up a Queue, a Timer, a TetrisModel, and a TetrisView.
     */
    public TetrisController() {
        queue = new LinkedList();
        timer = new Timer(ONE_TICK, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
                counter++;
                if((counter % (MAX_LEVEL - level + 1)) == 0) {
                    model.update();
                    view.update();
                }
            }
            
        });
        toolkit = Toolkit.getDefaultToolkit();
        model = new TetrisModel(this);
        view = new TetrisView(this);
        music = new TetrisMusic(this);
        sfx = new TetrisSFX(this);
        running = false;
        paused = false;
        
        view.setQuitButton(false);
        view.setPauseButton(false);
        view.setStartButton(true);
        view.togglePauseButtonText(paused);
        counter = 0;
        level = 1;
        highScore = 0;
    }
    
    /**
     * This method is called by the TetrisModel when it highlights lines of
     * blocks to clear so that the timer will back up one TICK in order to
     * display the highlighted lines long enough to be seen.
     */
    public void timerHiccup() {
        counter = 0;
    }
    
    /**
     * Inserts an object into the TetrisController's Queue.
     * This is used by the TetrisModel to send changes to the TetrisView.
     * @param object the Object to be inserted into the Queue
     */
    public void queueInsert(Object object) {
        queue.add(object);
    }

    /**
     * Removes the next object in the TetrisController's Queue and returns it.
     * This is used by the TetrisView to receive changes from the TetrisModel.
     * @return the next Object in the Queue
     */
    public Object queueRemove() {
        return queue.remove();
    }

    /**
     * Checks to see if there is a next object in the TetrisController's Queue.
     * This is used by the TetrisView to check if there are any changes to make.
     * @return true if there is an object in the Queue, false otherwise
     */
    public boolean queueHasNext() {
        return queue.peek() != null;
    }
    
    /**
     * Process the user input to the program and perform the necessary actions.
     * This method is mostly called by the TetrisView whenever it reads a key
     * or a button press, but sometimes the TetrisController will call this
     * on itself, such as when the game ends.
     * @param keyCode the code for what type of input was received
     */
    public void processInput(int keyCode) {
        if(!running) {
            if(keyCode == KeyEvent.VK_F10) { // START
                timer.start();
                model.resetModel();
                view.resetView();
                music.play();
                running = true;
                paused = false;
                view.setQuitButton(true);
                view.setPauseButton(true);
                view.setStartButton(false);
                view.togglePauseButtonText(paused);
                counter = 0;
                level = 1;
            }
            return;
        }
        if(keyCode == KeyEvent.VK_ESCAPE) { // QUIT
            timer.stop();
            music.stop();
            running = false;
            paused = false;
            view.setQuitButton(false);
            view.setPauseButton(false);
            view.setStartButton(true);
            view.togglePauseButtonText(paused);
            return;
        }
        if(paused) { // Check to see if PAUSE already active
            if(keyCode == 'p' || keyCode == 'P') {
                timer.start();
                music.play();
                paused = false;
                view.togglePauseButtonText(paused);
            }
            return;
        }
        switch(keyCode) {
            case KeyEvent.VK_LEFT: // MOVE LEFT
            case 'a':
            case 'A':
                if(model.tryMoveLeft()) view.update();
                else toolkit.beep();
                break;
            case KeyEvent.VK_RIGHT: // MOVE RIGHT
            case 'd':
            case 'D':
                if(model.tryMoveRight()) view.update();
                else toolkit.beep();
                break;
            case KeyEvent.VK_DOWN: // MOVE DOWN
            case 's':
            case 'S':
                if(model.tryMoveDown()) view.update();
                else toolkit.beep();
                break;
            case 'e': // ROTATE CLOCKWISE
            case 'E':
                if(model.tryRotateClockwise()) view.update();
                else toolkit.beep();
                break;
            case 'q': // ROTATE COUNTER CLOCKWISE
            case 'Q':
                if(model.tryRotateCounterClockwise()) view.update();
                else toolkit.beep();
                break;
            case KeyEvent.VK_UP: // HOLD EXCHANGE
            case 'w':
            case 'W':
                if(model.tryHoldExchange()) view.update();
                else toolkit.beep();
                break;
            case KeyEvent.VK_SPACE: // DROP
                model.drop();
                view.update();
                break;
            case 'p': // PAUSE
            case 'P':
                timer.stop();
                music.stop();
                paused = true;
                view.togglePauseButtonText(paused);
                break;
        }
    }
    
    /**
     * This method is called by the TetrisModel after it performs an update
     * so that the TetrisController can update the game statistics for level,
     * lines, score, and high score.
     * @param tetriminos_dropped the number of Tetriminos dropped
     * @param single_lines_cleared the number of single lines cleared
     * @param double_lines_cleared the number of double lines cleared
     * @param triple_lines_cleared the number of triple lines cleared
     * @param tetris_cleared the number of Tetris (4 lines at once) cleared
     */
    public void processStatistics(int tetriminos_dropped,
        int single_lines_cleared, int double_lines_cleared,
        int triple_lines_cleared, int tetris_cleared) {
        
        // Compute the level
        level = 1 + ((single_lines_cleared + double_lines_cleared +
            triple_lines_cleared + tetris_cleared) / CLEARS_PER_LEVEL);
        if(level > MAX_LEVEL) level = MAX_LEVEL;
        view.setLevelValue(level);
        
        // Compute the lines
        int lines = single_lines_cleared + 2 * double_lines_cleared +
            3 * triple_lines_cleared + 4 * tetris_cleared;
        view.setLinesValue(lines);
        
        // Compute the score
        int score = tetriminos_dropped + 100 * single_lines_cleared +
            200 * double_lines_cleared + 400 * triple_lines_cleared +
            800 * tetris_cleared;
        view.setScoreValue(score);
        
        // Change the high score if needed
        if(score > highScore) {
            highScore = score;
            view.setHighScoreValue(highScore);
        }  
    }
    
    /**
     * This method calls on TetrisSFX to play the clear line sound effect.
     */
    public void playCL() {
    	sfx.playCL();
    }
    
    /**
     * This method calls on TetrisSFX to play the Tetris sound effect.
     */
    public void playTetris() {
    	sfx.playTetris();
    }
    
    /**
     * This method is called by the TetrisModel when the TetriminoWell has
     * overflowed to let the TetrisController know that the game has ended.
     */
    public void gameOver() {
        processInput(KeyEvent.VK_ESCAPE);
        sfx.playGameOver();
        music.stop();
        view.displayGameOver();
    }
    
}
