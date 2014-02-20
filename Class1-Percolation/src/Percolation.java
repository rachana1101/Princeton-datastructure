/**
 * Percolation class.
 * 
 * @author rachana
 * 
 */
public class Percolation {
	/**
	 * Two dimensional array structure to create. percolation.
	 */
	private int[][] matrix;
	/**
	 * top row.
	 */
	private int top = 0;
	/**
	 * bottom row.
	 */
	private int bottom;
	/**
	 * The no of cells.
	 */
	private int N;
	/**
	 * weighted quick union class.
	 */
	private WeightedQuickUnionUF wQUFHelper;

	/**
	 * Constructor of the class.
	 * 
	 * @param N
	 *            no. of cells for 2 dimentional array.
	 */
	public Percolation(int N) {
		this.N = N;
		wQUFHelper = new WeightedQuickUnionUF(N * N + 2);
		matrix = new int[N][N];
		bottom = N * N + 1;
	}

	/**
	 * This method will help to open the cell.
	 * 
	 * @param i
	 *            - rowth cell to pen
	 * @param j
	 *            - column cell to open
	 */
	public void open(int i, int j) {
		matrix[i - 1][j - 1] = 1;
		if (i == 1) {
			wQUFHelper.union(xyToUF(i, j), top);
		}
		if (i == N) {
			wQUFHelper.union(xyToUF(i, j), bottom);
		}

		if (j > 1 && isOpen(i, j - 1)) {
			wQUFHelper.union(xyToUF(i, j), xyToUF(i, j - 1));
		}
		if (j < N && isOpen(i, j + 1)) {
			wQUFHelper.union(xyToUF(i, j), xyToUF(i, j + 1));
		}
		if (i > 1 && isOpen(i - 1, j)) {
			wQUFHelper.union(xyToUF(i, j), xyToUF(i - 1, j));
		}
		if (i < N && isOpen(i + 1, j)) {
			wQUFHelper.union(xyToUF(i, j), xyToUF(i + 1, j));
		}
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isOpen(int i, int j) {
		return matrix[i - 1][j - 1]==1;
	}

	/**
	 * 
	 * @param i
	 *            - row
	 * @param j
	 *            - column
	 * @return if cell is full or not
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isFull(int i, int j) {
		if (0 < i && i <= N && 0 < j && j <= N) {
			return wQUFHelper.connected(top, xyToUF(i, j));
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * check if the matrix can percolates or not.
	 * 
	 * @return boolean true or false
	 */
	public boolean percolates() {
		return wQUFHelper.connected(top, bottom);
	}

	/**
	 * 
	 * @param i
	 *            row
	 * @param j
	 *            column
	 * @return 1 dimensional position
	 * @throws IndexOutOfBoundsException
	 */
	private int xyToUF(int i, int j) {
		return N * (i - 1) + j;
	}
}