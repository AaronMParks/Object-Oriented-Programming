package UnitPackage;

public class NC1 implements INeighborCounter {
    boolean[][] cells;
    int rows = 0;
    int cols = 0;
    public NC1(boolean[][] cells) {
        this.cells = cells;
        rows = cells.length;
        cols = cells[0].length;
    }
    public int Count(int row, int col) {
        int n = 0;
        n += live(row-1, col-1);
        n += live(row-1, col);
        n += live(row-1, col+1);
        n += live(row, col+1);
        n += live(row+1, col+1);
        n += live(row+1, col);
        n += live(row+1, col-1);
        n += live(row, col-1);
        System.out.println("Count for cell [" + row + ", " + col + "]: " + n);
        return n;
    }
    public int live(int r, int c) {
        if (r < 0 || r >= rows) return 0;
        if (c < 0 || c >= cols) return 0;
        return (cells[r][c]) ? 1 : 0;
    }
}
