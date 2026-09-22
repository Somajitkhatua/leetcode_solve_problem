class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            
            sum += nums[right];

            // Shrink the window while sum >= target
            while (sum >= target) {
                
                minLength = Math.min(minLength, right - left + 1);

                sum -= nums[left];
                left++;
            }
        }

        // No valid subarray found
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}