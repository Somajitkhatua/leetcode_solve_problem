class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // A valid parentheses string must have even length.
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        // dp[i][j][balance] = whether we can reach (i,j)
        // with this balance of unmatched '('.
        boolean[][][] dp = new boolean[m][n][m + n];

        if (grid[0][0] == ')') {
            return false;
        }

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance < m + n; balance++) {

                    if (grid[i][j] == '(') {

                        if (i > 0 && dp[i - 1][j][balance]) {
                            dp[i][j][balance + 1] = true;
                        }

                        if (j > 0 && dp[i][j - 1][balance]) {
                            dp[i][j][balance + 1] = true;
                        }

                    } else {

                        // We can only add ')' if there is an
                        // unmatched '(' available.
                        if (balance == 0) {
                            continue;
                        }

                        if (i > 0 && dp[i - 1][j][balance]) {
                            dp[i][j][balance - 1] = true;
                        }

                        if (j > 0 && dp[i][j - 1][balance]) {
                            dp[i][j][balance - 1] = true;
                        }
                    }
                }
            }
        }

        // Valid parentheses string must finish with balance 0.
        return dp[m - 1][n - 1][0];
    }
}