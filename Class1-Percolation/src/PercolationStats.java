
/**
 * To calculate the statistics of the percolation class
 * @author rachana
 */
public class PercolationStats {
	private static final double CONFIDENCE = 1.96;
	/**
	 * The no of times the experiment will run.
	 */
	private int experimentsCount;
	/**
	 * Instance of percolation.
	 */
	private Percolation perculation;
	/**
	 * Array of fractions.
	 */
	private double[] fractions;
	/**
	 * constructor of the percolation stats. it runs the percolation for T no. of times 
	 * for the 2 dimensional matrix[N][N]
	 * @param N
	 * @param T
	 * @throws IllegalAccessException
	 */
	public PercolationStats(final int N, final int T) throws IllegalAccessException {
		// perform T independent computational experiments on an N-by-N grid
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException("Given N <= 0 || T <= 0");
		}
		//Total no of times we will run the percolation experiment
		experimentsCount = T;
		fractions = new double[experimentsCount];
		for (int num = 0; num < experimentsCount; num++) {
			//everytime create new instance of percolation for N*N cells
			perculation = new Percolation(N);
			int openedCells = 0;
			//check if it perculates or not 
			while (!perculation.percolates()) {
				//generate random # and try to open cell
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				//is the cell is not open, ope it 
				if (!perculation.isOpen(i, j)) {
					perculation.open(i, j);
					openedCells++;
				}
			}
			double fraction = (double) openedCells / (N * N); 
			fractions[num] = fraction;
		}
	}
	/**
	 * @return calculates the mean.
	 */
	public double mean() {
		return StdStats.mean(fractions);
	}
	/**
	 * 
	 * @return calculates stddev.
	 */
	public double stddev() {
		return StdStats.stddev(fractions);
	}
	/**
	 * Calculates the confidenceLo.
	 * @return confidencelo
	 */
	public double confidenceLo() {
		return mean() - ((CONFIDENCE * stddev()) / Math.sqrt(experimentsCount));
	}
	/**
	 * calculates confidenceHi.
	 * @return confidenceHi
	 */
	public double confidenceHi() {
		return mean() + ((CONFIDENCE * stddev()) / Math.sqrt(experimentsCount));
	}
	/**
	 * Main method to run the test.
	 * @param args
	 */
	public static void main(final String[] args) {
		// Get the 
		StdOut.println(" Enter No. of cells for the matrix ");
		int N = StdIn.readInt();
		StdOut.println(" Enter No. times to run experiment ");
		int T = StdIn.readInt();
		System.out.println(" N " + N + " T " + T);
		try {
			PercolationStats percolationStats = new PercolationStats(N, T);
			String confidence = percolationStats.confidenceLo() + ", "
					+ percolationStats.confidenceHi();
			StdOut.println("mean = " + percolationStats.mean());
			StdOut.println("stddev = " + percolationStats.stddev());
			StdOut.println("95% confidence interval = " + confidence);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
