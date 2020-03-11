import java.math.*;
import java.util.*;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class Add2Numbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int l1Len = 0, l2Len = 0;
        int addLen, val;
        boolean carry = false;
        ListNode result, temp;
        temp = l1;
        while (temp!=null) {
            l1Len++;
            temp = temp.next;
        }
        temp = l2;
        while (temp!=null) {
            l2Len++;
            temp = temp.next;
        }
        addLen = Math.min(l1Len, l2Len);

        result = null;
        while(addLen>0) {
            val = l1.val + l2.val;
            if(carry) {
                val++;
            }
            if((val/10) != 0) {
                val = val % 10;
                carry = true;
            } else {
                carry = false;
            }
            temp = new ListNode(val);
            temp.next = result;
            result = temp;
            l1 = l1.next;
            l2 = l2.next;
            addLen--;
        }
        result = reverse(result);
        temp = result;
        while(temp.next != null) {
            temp = temp.next;
        }
        if (l1Len > l2Len) {
            if(carry) {
                CarryForward(l1);
            }
            temp.next = l1;

        } else if (l1Len < l2Len) {
            if(carry) {
                CarryForward(l2);
            }
            temp.next = l2;
        } else {
            if(carry) {
                ListNode addLast = new ListNode(1);
                temp.next = addLast;
                addLast.next = null;
            }
        }

        return result;
    }

    ListNode reverse(ListNode node) {
        ListNode prev = null;
        ListNode current = node;
        ListNode next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        node = prev;
        return node;
    }

    void CarryForward (ListNode node) {
        boolean carry = true;
        ListNode prev = null;
        int val;
        while (carry && node!=null) {
            val = node.val + 1;
            if((val/10) != 0) {
                val = val % 10;
                carry = true;
            } else {
                carry = false;
            }
            node.val = val;
            prev = node;
            node = node.next;
        }
        if (node==null && carry) {
            ListNode addLast = new ListNode(1);
            prev.next = addLast;
            addLast.next = null;
        }
    }

}
