/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        // nums = [1]
        // 1 2 1 2 1 2
        // set(1)
        for (int num : nums) {
            set.add(num);
        }

        // nums = [1]
        // 0 2 1 2 1 
        // d h
        // p   c
        // set(1)
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curr = head,
                prev = dummy;

        while (curr != null) {
            if (set.contains(curr.val)) {
                prev.next = curr.next;
            } else {
                prev = curr;

            }
            curr = curr.next;
        }

        return dummy.next;
    }
}