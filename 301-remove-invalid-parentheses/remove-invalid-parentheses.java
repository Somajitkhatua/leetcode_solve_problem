import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        
        // BFS queue
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(s);
        visited.add(s);
        
        boolean found = false;
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            // If current string is valid, add it to result
            if (isValid(current)) {
                result.add(current);
                found = true;
            }
            
            // Once valid strings are found, don't remove more characters
            if (found) {
                continue;
            }
            
            // Generate strings by removing one parenthesis
            for (int i = 0; i < current.length(); i++) {
                
                // Only remove parentheses
                if (current.charAt(i) != '(' && current.charAt(i) != ')') {
                    continue;
                }
                
                String next = current.substring(0, i)
                             + current.substring(i + 1);
                
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        
        return result;
    }
    
    // Checks whether parentheses are valid
    private boolean isValid(String s) {
        int balance = 0;
        
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                balance++;
            } 
            else if (ch == ')') {
                balance--;
                
                // More closing parentheses than opening
                if (balance < 0) {
                    return false;
                }
            }
        }
        
        return balance == 0;
    }
}