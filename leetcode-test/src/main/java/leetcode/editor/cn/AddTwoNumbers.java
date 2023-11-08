/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * <p>
 * <p>
 * 示例 2：
 * <p>
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * <p>
 * <p>
 * 示例 3：
 * <p>
 * <p>
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 输出：[8,9,9,9,0,0,0,1]
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * 每个链表中的节点数在范围 [1, 100] 内
 * 0 <= Node.val <= 9
 * 题目数据保证列表表示的数字不含前导零
 * <p>
 * <p>
 * Related Topics 递归 链表 数学 👍 9951 👎 0
 */
package leetcode.editor.cn;

import com.azul.tooling.in.NetConnectionEvent;

public class AddTwoNumbers {
    public static void main(String[] args) {
        Solution solution = new AddTwoNumbers().new Solution();
        ListNode listNode = new ListNode(9, new ListNode());
        ListNode listNode2 = new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))))))));
        ListNode listNode1 = solution.addTwoNumbers(listNode, listNode2);
        System.out.println(listNode1);

    }

    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            //计算 然后拆分,long 都不够 换个思路
//            int num = 0;
//            long singlyLinked = getSinglyLinked(l1, num, 0);
//            long singlyLinked2 = getSinglyLinked(l2, num, 0);
//            Long resultNum = singlyLinked + singlyLinked2;
//            char[] arrs = resultNum.toString().toCharArray();
//            ListNode listNode = new ListNode();
//            ListNode nextListNode = new ListNode();
//            if (arrs.length==1){
//                listNode = new ListNode(arrs[0] - '0');
//                return listNode;
//            }
//            for (int i = 0; i <arrs.length; i++) {
//
//                if (i == 0) {
//                    // 字符 减少 0 不进行 实际的数学运算
//                    nextListNode = new ListNode(arrs[i] - '0');
//                } else if (i == arrs.length - 1) {
//
//                    listNode = new ListNode(arrs[i] - '0', nextListNode);
//                } else {
//                    nextListNode = new ListNode(arrs[i] - '0', nextListNode);
//                }
//            }
//
//            return listNode;


            return computeSum(l1, l2, 0);
        }

        public ListNode computeSum(ListNode node1, ListNode node2, int increment) {
            int sum = 0;
            if (node1 == null && node2 == null&& increment == 0) {
                return null;
            }

            int n1 = 0;
            int n2 = 0;
            ListNode next1 = null;
            ListNode next2 = null;
            if (node1 != null) {
                n1 = node1.val;
                next1 = node1.next;
            }
            if (node2 != null) {
                n2 = node2.val;
                next2 = node2.next;
            }


            sum = n1 + n2;
            sum = sum + increment;

            ListNode node = new ListNode(sum % 10);
            increment = sum >= 10 ? 1 : 0;
            node.next = computeSum(next1, next2, increment);
            return node;
        }


// 就是不让 一块加算啊这是
//        public long getSinglyLinked(ListNode netNode, long sum, int index) {
//            if (netNode == null) {
//                return sum;
//            } else {
//                return getSinglyLinked(netNode.next, (long) (sum + netNode.val * (Math.pow(10, index))), ++index);
//            }
//        }


//leetcode submit region end(Prohibit modification and deletion)

    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}