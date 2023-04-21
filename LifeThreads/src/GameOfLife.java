import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameOfLife {
    private Grid grid;
    private int generations;

    public GameOfLife(Grid grid, int generations) {
        this.grid = grid;
        this.generations = generations;
    }

    public void run() {
        for (int i = 0  ; i < generations; i++) {
            System.out.println("Generation " + (i + 1) + ":");
            System.out.println(grid);
            grid.nextGeneration();
        }
        System.out.println("Final generation");
        System.out.println(grid);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java GameOfLife <inputFile>");
            System.exit(1);
        }
        String inputFile = args[0];
        try {
            Scanner scanner = new Scanner(new File(inputFile));
            int rows = 20; // Set the number of rows directly to 20
            int cols = -1;
            Grid grid = new Grid(rows, rows); // Initialize the grid with 20x20 size
            int row = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                // Check if the current line contains only digits (assuming it's the number of generations)
                if (line.chars().allMatch(Character::isDigit)) {
                    try {
                        int generations = Integer.parseInt(line);
                        GameOfLife game = new GameOfLife(grid, generations);
                        game.run();
                    } catch (NumberFormatException e) {
                        System.err.println("Error: invalid number of generations: " + line);
                        System.exit(1);
                    }
                } else {
                    if (cols == -1) {
                        cols = line.length();
                    }
                    for (int col = 0; col < cols; col++) {
                        char c = line.charAt(col);
                        if (c == 'X') {
                            grid.setCell(row, col, new Cell(true));
                        } else if (c == '.') {
                            grid.setCell(row, col, new Cell(false));
                        } else {
                            System.err.println("Error: invalid character in input file: " + c);
                            System.exit(1);
                        }
                    }
                    row++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: input file not found");
            System.exit(1);
        }
    }
}
