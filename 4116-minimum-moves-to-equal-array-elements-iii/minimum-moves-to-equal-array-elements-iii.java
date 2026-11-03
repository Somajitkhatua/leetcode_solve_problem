
class Solution {
    public int minMoves(int[] nums) {

        int max = nums[0];
        int moves = 0;

        // Find maximum element
        for (int num : nums) {
            max = Math.max(max, num);
        }

        // Calculate minimum moves
        for (int num : nums) {
            moves += max - num;
        }

        return moves;
    }
}
