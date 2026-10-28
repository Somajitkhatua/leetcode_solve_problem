class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        java.util.BitSet[][] dp = new java.util.BitSet[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new java.util.BitSet(1024);

                if (i == 0 && j == 0) {
                    dp[i][j].set(grid[i][j]);
                    continue;
                }

                if (i > 0) {
                    addXors(dp[i][j], dp[i - 1][j], grid[i][j]);
                }

                if (j > 0) {
                    addXors(dp[i][j], dp[i][j - 1], grid[i][j]);
                }
            }
        }

        return dp[m - 1][n - 1].nextSetBit(0);
    }

    private void addXors(
        java.util.BitSet current,
        java.util.BitSet previous,
        int value
    ) {
        for (int x = previous.nextSetBit(0);
             x >= 0;
             x = previous.nextSetBit(x + 1)) {
            current.set(x ^ value);
        }
    }
}