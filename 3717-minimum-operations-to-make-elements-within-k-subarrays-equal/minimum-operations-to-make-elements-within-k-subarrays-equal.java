
import java.util.*;

class Solution {

    class Fenwick {
        long[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void add(int i, long val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;

            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }

            return sum;
        }

        int kth(long k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && tree[next] < k) {
                    idx = next;
                    k -= tree[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        int[] id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            ) + 1;
        }

        Fenwick count = new Fenwick(m);
        Fenwick sum = new Fenwick(m);

        // Calculate cost of every window of size x
        long[] cost = new long[n - x + 1];

        for (int i = 0; i < x; i++) {
            count.add(id[i], 1);
            sum.add(id[i], nums[i]);
        }

        for (int i = 0; i <= n - x; i++) {

            // Find median
            int mid = count.kth((x + 1L) / 2);
            long median = sorted[mid - 1];

            long leftCount = count.query(mid);
            long leftSum = sum.query(mid);

            long totalSum = sum.query(m);

            long rightCount = x - leftCount;
            long rightSum = totalSum - leftSum;

            // Minimum operations to equalize this window
            cost[i] =
                median * leftCount - leftSum
                + rightSum - median * rightCount;

            // Slide window
            if (i < n - x) {

                count.add(id[i], -1);
                sum.add(id[i], -nums[i]);

                count.add(id[i + x], 1);
                sum.add(id[i + x], nums[i + x]);
            }
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        // Zero subarrays require zero operations
        Arrays.fill(dp[0], 0);

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                // Skip current element
                dp[j][i] = dp[j][i - 1];

                // Select a window of size x
                if (i >= x && dp[j - 1][i - x] != INF) {

                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
