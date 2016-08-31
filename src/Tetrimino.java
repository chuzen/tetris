

import java.util.*;

/**
 * A Tetrimino is a geometric shape composed of four square blocks.
 * This class defines the basic Tetrimino object and its properties.
 * 
 * @author  John Cowgill, Baldwin Browne, Chunda Zeng
 * @version September 29, 2014
 */
public class Tetrimino {

    /**
     * The different shapes each Tetrimino may have.
     * Once set, the shape of a particular Tetrimino never changes.
     */
    public enum TetriminoShape {
        Shape_I, Shape_J, Shape_L, Shape_O, Shape_S, Shape_T, Shape_Z
    }
    
    /**
     * The different rotation angles each Tetrimino may have.
     * The angle increases clockwise and decreases counter clockwise.
     */
    public enum TetriminoRotation {
        Angle_0, Angle_90, Angle_180, Angle_270
    }
    
    /**
     * Coordinates of the four blocks for Tetriminos of every shape and rotation.
     * Given a 4 x 4 region (as shown below) the coordinates in this HashMap
     * indicate which four positions a particular Tetrimino occupies.
     * 
     *    0 1 2 3
     *  0 . . . .
     *  1 . . . .
     *  2 . . . .
     *  3 . . . .
     * 
     * Coordinates are specified as a { row, column } pair of integer values.
     * For example, a Shape_T Angle_0 Tetrimino would look like the following:
     * 
     *    0 1 2 3
     *  0 . . . .
     *  1 . T . .
     *  2 T T T .
     *  3 . . . .
     * 
     * So its coordinates would be: { 1, 1 }, { 2, 0 }, { 2, 1 }, and { 2, 2 }.
     */
    public static final HashMap<String, Integer[][]> TetriminoCoordinates =
        new HashMap<String, Integer[][]>() {{
        put(TetriminoShape.Shape_I + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 0 }, { 1, 1 }, { 1, 2 }, { 1, 3 }
        });
        put(TetriminoShape.Shape_I + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 0, 2 }, { 1, 2 }, { 2, 2 }, { 3, 2 }
        });
        put(TetriminoShape.Shape_I + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 0 }, { 2, 1 }, { 2, 2 }, { 2, 3 }
        });
        put(TetriminoShape.Shape_I + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_J + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 0 }, { 2, 0 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_J + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 1 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_J + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 0 }, { 2, 1 }, { 2, 2 }, { 3, 2 }
        });
        put(TetriminoShape.Shape_J + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 1 }, { 2, 1 }, { 3, 0 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_L + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_L + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 1 }, { 2, 1 }, { 3, 1 }, { 3, 2 }
        });
        put(TetriminoShape.Shape_L + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 0 }, { 2, 1 }, { 2, 2 }, { 3, 0 }
        });
        put(TetriminoShape.Shape_L + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 0 }, { 1, 1 }, { 2, 1 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_O + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_O + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_O + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_O + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_S + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 1 }, { 1, 2 }, { 2, 0 }, { 2, 1 }
        });
        put(TetriminoShape.Shape_S + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 1 }, { 2, 1 }, { 2, 2 }, { 3, 2 }
        });
        put(TetriminoShape.Shape_S + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 1 }, { 2, 2 }, { 3, 0 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_S + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 0 }, { 2, 0 }, { 2, 1 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_T + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 1 }, { 2, 0 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_T + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 1 }, { 2, 1 }, { 2, 2 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_T + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 0 }, { 2, 1 }, { 2, 2 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_T + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 1 }, { 2, 0 }, { 2, 1 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_Z + " " + TetriminoRotation.Angle_0, new Integer[][] {
            { 1, 0 }, { 1, 1 }, { 2, 1 }, { 2, 2 }
        });
        put(TetriminoShape.Shape_Z + " " + TetriminoRotation.Angle_90, new Integer[][] {
            { 1, 2 }, { 2, 1 }, { 2, 2 }, { 3, 1 }
        });
        put(TetriminoShape.Shape_Z + " " + TetriminoRotation.Angle_180, new Integer[][] {
            { 2, 0 }, { 2, 1 }, { 3, 1 }, { 3, 2 }
        });
        put(TetriminoShape.Shape_Z + " " + TetriminoRotation.Angle_270, new Integer[][] {
            { 1, 1 }, { 2, 0 }, { 2, 1 }, { 3, 0 }
        });
    }};

    /**
     * The shape and rotation for this Tetrimino.
     */
    private TetriminoShape shape;
    private TetriminoRotation rotation;
    
    /**
     * Constructor for a new Tetrimino of a random shape and rotation.
     * (This method is primarily used for testing.)
     */
    public Tetrimino() {
        List l = new ArrayList();
        for(TetriminoShape s: TetriminoShape.values()) {
            l.add(s);
        }
        Collections.shuffle(l);
        shape = (TetriminoShape) l.get(0);
        l.clear();
        for(TetriminoRotation r: TetriminoRotation.values()) {
            l.add(r);
        }
        Collections.shuffle(l);
        rotation = (TetriminoRotation) l.get(0);
    }
    
    /**
     * Constructor for a new Tetrimino of a specific shape and rotation Angle_0.
     * @param s the TetriminoShape for the new Tetrimino
     */
    public Tetrimino(TetriminoShape s) {
        shape = s;
        rotation = TetriminoRotation.Angle_0;
    }
    
    /**
     * Get this Tetrimino's shape.
     * @return the TetriminoShape
     */
    public TetriminoShape getShape() {
        return shape;
    }
    
    /**
     * Get this Tetrimino's rotation.
     * @return the TetriminoRotation
     */
    public TetriminoRotation getRotation() {
        return rotation;
    }
    
    /**
     * Get this Tetrimino's coordinates.
     * @return an Integer[4][2] array
     */
    public Integer[][] getCoordinates() {
        return TetriminoCoordinates.get(shape + " " + rotation);
    }
    
    /**
     * Get the character symbol associated with the shape of this Tetrimino.
     * @return a char representing the shape
     */
    public char getSymbol() {
        switch(shape) {
            case Shape_I: return 'I';
            case Shape_J: return 'J';
            case Shape_L: return 'L';
            case Shape_O: return 'O';
            case Shape_S: return 'S';
            case Shape_T: return 'T';
            case Shape_Z: return 'Z';
            default: return '?';
        }
    }
    
    /**
     * Check to see if this Tetrimino has a block at the specified coordinates.
     * @param row the row coordinate to check
     * @param column the column coordinate to check
     * @return true/false whether this Tetrimino has a block at the coordinates.
     */
    public boolean hasCoordinates(int row, int column) {
        Integer[][] coordinates = getCoordinates();
        for(int i = 0; i < 4; i++) {
            if(coordinates[i][0] == row && coordinates[i][1] == column) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Increase the rotation angle by 90 degrees for this Tetrimino.
     */
    public void rotateClockwise() {
        switch(rotation) {
            case Angle_0:
                rotation = TetriminoRotation.Angle_90;
                break;
            case Angle_90:
                rotation = TetriminoRotation.Angle_180;
                break;
            case Angle_180:
                rotation = TetriminoRotation.Angle_270;
                break;
            case Angle_270:
                rotation = TetriminoRotation.Angle_0;
                break;
        }
    }
    
    /**
     * Decrease the rotation angle by 90 degrees for this Tetrimino.
     */
    public void rotateCounterClockwise() {
        switch(rotation) {
            case Angle_0:
                rotation = TetriminoRotation.Angle_270;
                break;
            case Angle_90:
                rotation = TetriminoRotation.Angle_0;
                break;
            case Angle_180:
                rotation = TetriminoRotation.Angle_90;
                break;
            case Angle_270:
                rotation = TetriminoRotation.Angle_180;
                break;
        }
    }
    
    /**
     * Generate a printable description for this Tetrimino.
     * @return the String description
     */
    @Override
    public String toString() {
        return shape + " " + rotation;
    }
    
    /**
     * Prints a representation of this Tetrimino to the console.
     */
    public void displayTetrimino() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(hasCoordinates(i, j)) System.out.print(getSymbol() + " ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }
    
    /**
     * Test routine.
     */
    public static void testTetrimino() {
        Tetrimino testTetrimino;
        for(TetriminoShape s: TetriminoShape.values()) {
            testTetrimino = new Tetrimino(s);
            for(TetriminoRotation r: TetriminoRotation.values()) {
                System.out.println(testTetrimino);
                testTetrimino.displayTetrimino();
                testTetrimino.rotateClockwise();
            }
        }
    }
    
}
