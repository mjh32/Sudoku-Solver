package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.tests.Examples;
import java.io.FileWriter;

/**
 * The Main class of the Program
 * Contains methods to solve all 400 puzzles found in the Examples class and write the results to a file
 */
public class Main {
    /**
     * The main method uses a for loop to attempt to solve all 400 puzzles and prints the results
     * @param args standard parameter for a main method
     */
    public static void main(String[] args) {
        int[] gaps = new int[400];
        float[] times = new float[400];
        long startEX = System.currentTimeMillis();
        for (int i=0;i<400;i++) {
            Solver solving = new Solver<>(Examples.getExample(i));
            gaps[i] = solving.getGaps();
            long start = System.currentTimeMillis();
            boolean solved = solving.solve();
            long end = System.currentTimeMillis() - start;
            times[i] = ((float) end) / 1000;
            if (!solved) {
                System.out.print("\nERROR: Following Puzzle is unsolvable");
                break;
            }
            System.out.printf("\nPuzzle #%d\n%d empty cells\n%.3f seconds taken\n", (i+1), gaps[i], times[i]);
            if (i == 399) {
                long endEX = System.currentTimeMillis() - startEX;
                System.out.printf("\nAll puzzles attempted :)\n%.2f overall minutes taken\n", (((float) (endEX / 1000)) / 60));
                writeToFile(times);
            }
        }
    }

    /**
     * Calculates and writes the mean of the time taken for the puzzles with a certain number of empty cells
     * @param times the array of the time taken for each puzzle
     */
    private static void writeToFile(float[] times) {
        try {
            FileWriter outputFile = new FileWriter("output.txt");
            for (int i=0;i<49;i++) {
                float sum = 0;
                for (int j=0;j<8;j++) {
                    sum += times[j+(i*8)];
                }
                String output = String.format("%.3f\n", (sum / 8));
                outputFile.write(output);
            }
            outputFile.close();
            System.out.println("Output file written to.");
        } catch (Exception e) {
            System.out.println("Cannot output results to text file, file missing.");
        }
    }
}
