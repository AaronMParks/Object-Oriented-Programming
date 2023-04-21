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
    public void nextGeneration() {
        Grid newGrid = new Grid(cells.length, cells[0].length);
        for (int i = 0;i<cells.length;i++) {
            for (int j = 0;j < cells[0].length;j++) {
                int neighbors = countNeighbors(i,j);
                if (cells[i][j].isAlive()) {
                    if (neighbors < 2 || neighbors > 3) {
                        newGrid.setCell(i,j, new Cell(false));
                    } else {
                        newGrid.setCell(i,j, new Cell(true));
                    }
                } else {
                    if (neighbors == 3) {
                        newGrid.setCell(i,j,new Cell(true));
                    } else {
                        newGrid.setCell(i,j,new Cell(false));
                    }
                }
            }
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
