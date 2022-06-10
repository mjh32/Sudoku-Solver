package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.interfaces.IGrid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The Grid Class is used to define the 9x9 Sudoku Puzzle
 * It contains methods to get and set cells within the grid
 * Also has a method to check whether the grid is a valid Sudoku Grid
 * @param <E> The class implements the IGrid interface
 */
public class Grid<E> implements IGrid {
    private E[][] sudokuGrid;

    /**
     * Initialises the 2D array to contain the Sudoku Puzzle
     * Fills the grid with empty cells to avoiding using nulls
     */
    public Grid() {
        sudokuGrid = (E[][]) new Object[9][9];
        for (int y=0;y<9;y++) {
            for (int x=0;x<9;x++) {
                sudokuGrid[x][y] = (E) Integer.valueOf(0);
            }
        }
    }

    /**
     * Sets the specified cell to a specified value
     * @param x column number
     * @param y row number
     * @param digit specified value
     * @throws IGrid.BadCellException thrown if cell is out of range
     * @throws IGrid.BadDigitException thrown if value is out of range
     */
    public void set(int x, int y, int digit) throws IGrid.BadCellException, IGrid.BadDigitException {
        if (digit < 0 || digit > 9) {
            throw new IGrid.BadDigitException(digit);
        } else {
            try {
                sudokuGrid[x][y] = (E) Integer.valueOf(digit);
            } catch (Exception e) {
                throw new IGrid.BadCellException();
            }
        }
    }

    /**
     * Gets the value from a specified cell
     * @param x column number
     * @param y row number
     * @return returns the desired value
     * @throws IGrid.BadCellException thrown if the cell is out of range
     */
    public int get(int x, int y) throws IGrid.BadCellException {
        try {
            return (int) sudokuGrid[x][y];
        } catch (Exception e) {
            throw new IGrid.BadCellException();
        }
    }

    /**
     * Iterates through all cells within the grid to check it adheres to the rules of a Sudoku Puzzle
     * Three Hash Sets are used to check for duplicate values within the rows, columns and 3x3 sub-grids
     * Sub-grids are checked separately to rows and columns in-order to re-use already initialised Hash Sets
     * @return returns true if grid is valid, false if it's not
     */
    public boolean isValid() {
        int value;
        int zero = 0;
        Integer[] numbers = new Integer[9];
        Set<Integer> numbers1 = new HashSet<>(Arrays.asList(numbers));
        Set<Integer> numbers2 = new HashSet<>(Arrays.asList(numbers));
        Set<Integer> numbers3 = new HashSet<>(Arrays.asList(numbers));
        numbers1.clear();
        numbers2.clear();
        for (int y=0;y<9;y++) {
            for (int x=0;x<9;x++) {
                value = (int) sudokuGrid[x][y];
                if (value == 0) {
                    zero--;
                    value = zero;
                }
                if (!numbers1.add(value)) {
                    return false;
                }
                value = (int) sudokuGrid[y][x];
                if (value == 0) {
                    zero--;
                    value = zero;
                }
                if (!numbers2.add(value)) {
                    return false;
                }
            }
            numbers1.clear();
            numbers2.clear();
        }
        numbers1.clear();
        numbers2.clear();
        numbers3.clear();
        for (int y=0;y<9;y++) {
            for (int x=0;x<9;x++) {
                value = (int) sudokuGrid[x][y];
                if (value == 0) {
                    zero--;
                    value = zero;
                }
                if (x>-1 && x<3) {
                    if (!numbers1.add(value)) {
                        return false;
                    }
                } else if (x>2 && x<6) {
                    if (!numbers2.add(value)) {
                        return false;
                    }
                } else if (x>5 && x<9) {
                    if (!numbers3.add(value)) {
                        return false;
                    }
                }
            }
            if (y==2 || y==5 || y==8) {
                numbers1.clear();
                numbers2.clear();
                numbers3.clear();
            }
        }
        return true;
    }
}
