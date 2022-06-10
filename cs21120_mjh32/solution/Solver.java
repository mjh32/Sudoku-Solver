package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.interfaces.ISolver;

/**
 * The Solver Class is used to solve a Sudoku Puzzle
 * It Contains methods to return the loaded Sudoku Grid and find the number of empty cells
 * Also contains a method to solve the Puzzle held by the Grid
 * @param <E> The class implements the ISolver interface
 */
public class Solver<E> implements ISolver {
    private IGrid sudokuGrid;
    private int gaps;

    /**
     * Initialises the class and assigns values to variables
     * @param g the grid containing the Sudoku Puzzle
     */
    public Solver(IGrid g) {
        sudokuGrid = g;
        gaps = 0;
    }

    /**
     * Returns the all values within the grid
     * @return returns a string containing all the values
     */
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int y=0;y<9;y++) {
            for (int x=0;x<9;x++) {
                b.append(sudokuGrid.get(x,y));
            }
            b.append('\n');
        }
        return b.toString();
    }

    /**
     * Returns the number of empty cells within the grid
     * @return returns an integer number
     */
    public int getGaps() {
        for (int y=0;y<9;y++) {
            for (int x = 0; x < 9; x++) {
                if (sudokuGrid.get(x,y) == 0) {
                    gaps++;
                }
            }
        }
        return gaps;
    }

    /**
     * Attempts to solve the Sudoku Puzzle
     * Iterates through the whole grid trying all possible values at each empty cell
     * Uses the isValid() method from the Grid Class to check whether the placed value is correct
     * @return returns true if the grid was solved, false if it was not
     */
    public boolean solve() {
        for (int y=0;y<9;y++) {
            for (int x=0;x<9;x++) {
                if (sudokuGrid.get(x,y) == 0) {
                    for (int digit=1;digit<10;digit++) {
                        sudokuGrid.set(x,y,digit);
                        if (sudokuGrid.isValid()) {
                            boolean result = solve();
                            if (result == true) {
                                return true;
                            }
                        }
                    }
                    sudokuGrid.set(x,y,0);
                    return false;
                }
            }
        }
        return true;
    }
}
