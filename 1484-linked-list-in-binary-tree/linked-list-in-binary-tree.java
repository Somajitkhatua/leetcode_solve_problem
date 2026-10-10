
class Solution {
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) {
            return false;
        }

        // Try matching the list starting from this tree node
        if (check(head, root)) {
            return true;
        }

        // Try starting from the left or right subtree
        return isSubPath(head, root.left) ||
               isSubPath(head, root.right);
    }

    private boolean check(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }

        if (root == null || head.val != root.val) {
            return false;
        }

        // Continue along either downward path
        return check(head.next, root.left) ||
               check(head.next, root.right);
    }
}
