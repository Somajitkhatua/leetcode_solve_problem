class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Reverse alphabet position
            int reverseValue = 'z' - ch + 1;

            // Position in string is i + 1
            sum += reverseValue * (i + 1);
        }

        return sum;
    }
}