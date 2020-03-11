import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * Use level order traversal for serialize and
 * use left = (index-number_of_nulls_encountered_till_now)*2 and right = (index-number_of_nulls_encountered_till_now)*2 + 1 to deserialize
 * Note: index start from 1.
 * Why use number_of_nulls_encountered_till_now?
 * Example array:
 * Array : 5 2 3 n n 2 4 3 1
 * Index : 1 2 3 4 5 6 7 8 9
 *              5
 *          /       \
 *         2         3
 *       /  \      /   \
 *      n    n    2     4
 *              /  \
 *            3     1
 *
 *  Now, generally formula to get left-child is index*2
 *      but in case of node 2 (index = 6) it will fail because 2*6 = 12 and there is no node at index 12.
 *      why is it failing? because we have null values in between. Fix it using number_of_nulls_encountered_till_now.
 * */
public class Codec {

    HashMap<Integer, TreeNode> hm = new HashMap<>();
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serialized = new StringBuilder();
        Queue<TreeNode> q = new LinkedList();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode temp = q.remove();
            if(temp != null) {
                q.add(temp.left);
                q.add(temp.right);
                serialized.append(temp.val);
            }
            serialized.append('&');
        }
        return serialized.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] dataArray = data.split("&");
        int dataArraySize = dataArray.length;
        TreeNode head = null;
        int numOFNulls = 0;
        for (int i = 0; i<dataArraySize; i++) {
            int left, right;
            TreeNode leftnode = null, rightNode = null, currentNode = null;
            if (!dataArray[i].equals("")) {
                left = 2*(i-numOFNulls+1)-1;
                right = 2*(i-numOFNulls+1);
                if (left < dataArraySize) {
                    leftnode = dataArray[left].equals("")? null : hm.getOrDefault(left, new TreeNode(Integer.parseInt(dataArray[left])));
                }
                if (right < dataArraySize) {
                    rightNode = dataArray[right].equals("")? null : new TreeNode(Integer.parseInt(dataArray[right]));
                }
                currentNode = hm.getOrDefault(i,new TreeNode(Integer.parseInt(dataArray[i])));
                currentNode.left = leftnode;
                currentNode.right = rightNode;
                if (i==0) {
                    head = currentNode;
                }
                if (!hm.containsKey(i)) hm.put(i,currentNode);
                if (!hm.containsKey(left)) hm.put(left,leftnode);
                if (!hm.containsKey(right)) hm.put(right,rightNode);
            } else {
                numOFNulls++;
            }
        }
        return head;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));