import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;

        // best[i] = minimum length of a valid subarray
        // ending at or before index i
        int[] best = new int[n];

        Arrays.fill(best, Integer.MAX_VALUE);

        int left = 0;
        int sum = 0;
        int answer = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Since all numbers are positive,
            // shrink the window when sum becomes too large.
            while (sum > target) {
                sum -= arr[left++];
            }

            // Found a subarray with sum == target
            if (sum == target) {
                int length = right - left + 1;

                // If there is a previous non-overlapping subarray
                if (left > 0 && best[left - 1] != Integer.MAX_VALUE) {
                    answer = Math.min(
                        answer,
                        length + best[left - 1]
                    );
                }

                // Store the shortest valid subarray
                // ending at or before 'right'
                if (right == 0) {
                    best[right] = length;
                } else {
                    best[right] = Math.min(best[right - 1], length);
                }
            } else {
                // Carry forward the previous best answer
                if (right > 0) {
                    best[right] = best[right - 1];
                }
            }
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}