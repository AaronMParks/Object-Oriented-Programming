package UnitPackage;

import static org.junit.jupiter.api.Assertions.*;

class NC1Test {

    @org.junit.jupiter.api.Test
        public void testCount() {
            boolean[][] cells = {{true, false, true},
                    {false, false, true},
                    {false, true, false}
            };
            INeighborCounter nc = new NC1(cells);
        int[][] expected = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                int actual = nc.Count(r, c);
                System.out.println("Expected: " + expected[r][c] + ", Actual: " + actual);
                assertEquals(expected[r][c], actual);
            }
        }

            assertEquals(1, nc.Count(0, 0)); // top left corner
            assertEquals(2, nc.Count(0, 1)); // top edge
            assertEquals(3, nc.Count(0, 2)); // top right corner
            assertEquals(3, nc.Count(1, 0)); // left edge
            assertEquals(3, nc.Count(1, 2)); // right edge
            assertEquals(3, nc.Count(2, 0)); // bottom left corner
            assertEquals(2, nc.Count(2, 1)); // bottom edge
            assertEquals(1, nc.Count(2, 2)); // bottom right corner

        boolean[][] allDead = {{false, false, false},
                {false, false, false},
                {false, false, false}};
        nc = new NC1(allDead);

        assertEquals(0, nc.Count(0, 0)); // top left corner of all dead grid
        assertEquals(0, nc.Count(1, 1)); // middle of all dead grid
        assertEquals(0, nc.Count(2, 2)); // bottom right corner of all dead grid

        boolean[][] allAlive = {{true, true, true},
                {true, true, true},
                {true, true, true}};
        nc = new NC1(allAlive);

        assertEquals(8, nc.Count(1, 1)); // middle of all alive grid

        // Test out-of-bounds indices
        assertEquals(0, nc.Count(-1, 0)); // row index too small
        assertEquals(0, nc.Count(0, -1)); // column index too small

    }
    public void testCountOnSingleCellGrid() {
        boolean[][] cells = {{true}};
        NC1 nc = new NC1(cells);

        assertEquals(0, nc.Count(0, 0)); // single cell should have 0 neighbors
    }
    public void testCountOn2x2Grid() {
        boolean[][] cells = {{false, true},
                {true, false}};
        NC1 nc = new NC1(cells);

        assertEquals(2, nc.Count(0, 0)); // top left corner
        assertEquals(2, nc.Count(0, 1)); // top right corner
        assertEquals(2, nc.Count(1, 0)); // bottom left corner
        assertEquals(1, nc.Count(1, 1)); // bottom right corner
    }
    public void testCountOnEmptyGrid() {
        boolean[][] cells = {{}};
        NC1 nc = new NC1(cells);

        assertEquals(0, nc.Count(0, 0)); // empty grid should have 0 neighbors
    }
    public void testCountOnLargeGrid() {
        boolean[][] cells = new boolean[100][100];
        cells[50][50] = true;
        NC1 nc = new NC1(cells);

        assertEquals(0, nc.Count(0, 0)); // top left corner
        assertEquals(0, nc.Count(99, 99)); // bottom right corner
        assertEquals(8, nc.Count(50, 50)); // middle
        assertEquals(3, nc.Count(0, 50)); // top edge
        assertEquals(3, nc.Count(50, 0)); // left edge
        assertEquals(3, nc.Count(99, 50)); // bottom edge
        assertEquals(3, nc.Count(50, 99)); // right edge
    }
    public void testCountOnEdgeCells() {
        boolean[][] cells = {{true, false, true},
                {false, false, true},
                {false, true, false}};
        NC1 nc = new NC1(cells);

        assertEquals(1, nc.Count(0, 0)); // top left corner
        assertEquals(1, nc.Count(0, 2)); // top right corner
        assertEquals(1, nc.Count(2, 0)); // bottom left corner
        assertEquals(1, nc.Count(2, 2)); // bottom right corner
        assertEquals(2, nc.Count(0, 1)); // top edge
        assertEquals(2, nc.Count(1, 0)); // left edge
        assertEquals(2, nc.Count(2, 1)); // bottom edge
        assertEquals(2, nc.Count(1, 2)); // right edge
    }
    public void testCountAllFalse4x4Grid() {
        boolean[][] cells = {{false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false}};
        INeighborCounter nc = new NC1(cells);
        int[][] expected = {{0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                int actual = nc.Count(r, c);
                System.out.println("Expected: " + expected[r][c] + ", Actual: " + actual);
                assertEquals(expected[r][c], actual);
            }
        }
    }
    public void testCountAllTrue4x4Grid() {
        boolean[][] cells = {{true, true, true, true},
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true}};
        INeighborCounter nc = new NC1(cells);
        int[][] expected = {{0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                int actual = nc.Count(r, c);
                System.out.println("Expected: " + expected[r][c] + ", Actual: " + actual);
                assertEquals(expected[r][c], actual);
            }
        }
    }





}