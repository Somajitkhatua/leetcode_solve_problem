
import java.util.*;

class Solution {
    public String[] createGrid(int m, int n) {
        String[] grid = new String[m];

        for (int i = 0; i < m; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '#');

            if (i == 0) {
                Arrays.fill(row, '.');
            } else {
                row[n - 1] = '.';
            }

            grid[i] = new String(row);
        }

        return grid;
    }
}
