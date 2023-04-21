import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Grid {
    private Cell[][] cells;

    public Grid(int rows, int cols) {
        cells = new Cell[rows][cols];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {
                cells[i][j] = new Cell(false);
            }
        }
    }
    public Cell getCell(int row, int cols) {
        return cells[row][cols];
    }
    public void setCell(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }
    public int countNeighbors(int row, int col) {
        int count = 0;
        for (int i = row-1;i <= row+1;i++) {
            for (int j = col-1;j <= col+1;j++) {
                if (i >= 0 && i < cells.length && j >= 0 && j < cells.length && !(i==row &&  j==col)) {
                    if (cells[i][j].isAlive()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // The path I chose is called "Fixed-size thread pooling", essentially it handles the cells
    // in parallel to create higher performance.
    // To make this program multi-threaded, the function below was the only function edited
    // When doing research into ways to thread a program and how to do so, this was the most
    // popular way to improve efficiency as well as reducing the number of threads needed to
    // perform.

    public void nextGeneration() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Grid newGrid = new Grid(cells.length, cells[0].length);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                final int row = i;
                final int col = j;
                executor.execute(() -> {
                    // System.out.println("Thread: " + Thread.currentThread().getName());
                    // the line above prints the pool and thread numbers if uncommented
                    int neighbors = countNeighbors(row, col);
                    if (cells[row][col].isAlive()) {
                        if (neighbors < 2 || neighbors > 3) {
                            newGrid.setCell(row, col, new Cell(false));
                        } else {
                            newGrid.setCell(row, col, new Cell(true));
                        }
                    } else {
                        if (neighbors == 3) {
                            newGrid.setCell(row, col, new Cell(true));
                        } else {
                            newGrid.setCell(row, col, new Cell(false));
                        }
                    }
                });
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cells = newGrid.cells;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < cells.length; i++) {
            for (int j = 0;j < cells[0].length;j++) {
                sb.append(cells[i][j].isAlive() ? 'X' : '.');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
