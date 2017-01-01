public class SudokuSolver {

	int[][] grid;

	public SudokuSolver(int[][] grid) {
		if (isNotSquare(grid) || getIntSqrt(grid.length) < 0) {
			throw new IllegalArgumentException("Grid is malformed.");
		}
		this.grid = grid;
	}

	private static boolean isNotSquare(int[][] grid) {
		int size = grid.length;

		for (int[] row : grid) {
			if (row.length != size) {
				return true;
			}
		}
		return false;
	}

	private static int getIntSqrt(int n) {
		int closeRoot = (int) Math.sqrt(n);
		return closeRoot * closeRoot == n ? closeRoot : -1;
	}

	private boolean checkRow(int row) {
		boolean[] filled = new boolean[this.grid.length];

		for (int i = 0; i < this.grid.length; i++) {
			int number;
			if ((number = this.grid[row][i] - 1) >= 0) {
				if (filled[number]) {
					return false;
				} else {
					filled[number] = true;
				}
			}
		}

		return true;
	}

	private boolean checkColumn(int column) {
		boolean[] filled = new boolean[this.grid.length];

		for (int i = 0; i < this.grid.length; i++) {
			int number;
			if ((number = this.grid[i][column] - 1) >= 0) {
				if (filled[number]) {
					return false;
				} else {
					filled[number] = true;
				}
			}
		}

		return true;
	}

	private boolean checkBox(int row, int column) {
		boolean[] filled = new boolean[this.grid.length];
		int sqrt = getIntSqrt(this.grid.length);
		int rowOffset = row / sqrt * sqrt;
		int columnOffset = column / sqrt * sqrt;

		for (int i = 0; i < sqrt; i++) {
			for (int j = 0; j < sqrt; j++) {
				int number;
				if ((number = grid[i + rowOffset][j + columnOffset] - 1) >= 0) {
					if (filled[number]) {
						return false;
					} else {
						filled[number] = true;
					}
				}
			}
		}

		return true;
	}

	private boolean doChecks(int row, int column) {
		return checkRow(row) && checkColumn(column) && checkBox(row, column);
	}

	private boolean recurSolve(int row, int column) {
		int nextSquare = 1 + row + column * this.grid.length;
		int nextRow = nextSquare % this.grid.length;
		int nextColumn = nextSquare / this.grid.length;

		if (nextSquare - 1 == this.grid.length * this.grid.length) {
			return true;
		} else if (this.grid[row][column] == 0) {
			for (int candidate = 1; candidate <= this.grid.length; candidate++) {

				this.grid[row][column] = candidate;
				if (doChecks(row, column) && recurSolve(nextRow, nextColumn)) {
					return true;
				}
			}
			this.grid[row][column] = 0;
			return false;
		} else {
			return doChecks(row, column) && recurSolve(nextRow, nextColumn);
		}
	}

	public boolean solve() {
		return recurSolve(0, 0);
	}

	public static void printGrid(int[][] grid) {
		for (int[] row : grid) {
			for (int digit : row) {
				if (digit == 0) {
					System.out.print(" ");
				} else {
					System.out.format("%d", digit);
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] grid = {
			{0, 7, 0, 2, 3, 8, 0, 0, 0},
			{0, 0, 0, 7, 4, 0, 8, 0, 9},
			{0, 6, 8, 1, 0, 9, 0, 0, 2},
			{0, 3, 5, 4, 0, 0, 0, 0, 8},
			{6, 0, 7, 8, 0, 2, 5, 0, 1},
			{8, 0, 0, 0, 0, 5, 7, 6, 0},
			{2, 0, 0, 6, 0, 3, 1, 9, 0},
			{7, 0, 9, 0, 2, 1, 0, 0, 0},
			{0, 0, 0, 9, 7, 4, 0, 8, 0}
		};

		SudokuSolver ss = new SudokuSolver(grid);
		printGrid(grid);
		System.out.println(ss.solve());
		printGrid(grid);
	}
}
