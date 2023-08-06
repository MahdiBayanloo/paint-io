public class MatrixConverter {
    private final int[][] matrix;
    private final int numRows;
    private final int numCols;

    public MatrixConverter(int[][] matrix) {
        this.matrix = matrix;
        this.numRows = matrix.length;
        this.numCols = matrix[0].length;
    }

    public void convertZeros() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (matrix[row][col] == 0) {
                    if (isInsideClosedLoop(row, col)) {
                        convertConnectedComponent(row, col);
                    }
                }
            }
        }
    }

    private boolean isInsideClosedLoop(int row, int col) {
        boolean[][] visited = new boolean[numRows][numCols];
        return dfs(row, col, visited);
    }

    private boolean dfs(int row, int col, boolean[][] visited) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            return false;
        }

        if (visited[row][col] || matrix[row][col] != 0) {
            return true;
        }

        visited[row][col] = true;

        boolean insideClosedLoop = true;
        insideClosedLoop &= dfs(row - 1, col, visited);
        insideClosedLoop &= dfs(row + 1, col, visited);
        insideClosedLoop &= dfs(row, col - 1, visited);
        insideClosedLoop &= dfs(row, col + 1, visited);

        return insideClosedLoop;
    }

    private void convertConnectedComponent(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || matrix[row][col] != 0) {
            return;
        }

        matrix[row][col] = 2;

        convertConnectedComponent(row - 1, col);
        convertConnectedComponent(row + 1, col);
        convertConnectedComponent(row, col - 1);
        convertConnectedComponent(row, col + 1);
    }

    public void printMatrix() {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

   /* public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 2, 2, 2},
                {0, 2, 2, 0, 2},
                {0, 2, 0, 2, 2},
                {0, 2, 0, 2, 0},
                {0, 2, 2, 2, 0}
        };

        MatrixConverter converter = new MatrixConverter(matrix);
        converter.convertZeros();
        converter.printMatrix();
    }*/
}