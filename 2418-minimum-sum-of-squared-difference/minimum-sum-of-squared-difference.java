
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        long k = (long) k1 + k2;
        int max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        // If all differences can be eliminated
        long total = 0;
        for (int d : diff) {
            total += d;
        }

        if (k >= total) {
            return 0;
        }

        // Binary search for the minimum achievable maximum difference
        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long remaining = k;

        // Reduce every difference above the selected level
        for (int i = 0; i < n; i++) {
            if (diff[i] > level) {
                remaining -= diff[i] - level;
                diff[i] = level;
            }
        }

        // Use remaining operations to reduce the largest differences
        // by one more, since these reductions give the greatest benefit.
        java.util.Arrays.sort(diff);

        for (int i = n - 1; i >= 0 && remaining > 0; i--) {
            if (diff[i] > 0) {
                diff[i]--;
                remaining--;
            }
        }

        long answer = 0;

        for (int d : diff) {
            answer += (long) d * d;
        }

        return answer;
    }
}
