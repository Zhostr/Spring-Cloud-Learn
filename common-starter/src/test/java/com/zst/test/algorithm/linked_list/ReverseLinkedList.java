package com.zst.test.algorithm.linked_list;

/**
 * @description: 反转链表
 * @author: Zhoust
 * @date: 2020/01/12 下午5:17
 * @version: V1.0
 */
public class ReverseLinkedList {

    /**
     * 反转相邻节点（https://leetcode.com/problems/swap-nodes-in-pairs/）
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode x = pre;

        while(pre.next != null && pre.next.next != null) {
            ListNode index = pre.next;
            ListNode next = index.next;

            pre.next = next;
            index.next = next.next;
            next.next = index;

            pre = index;
        }
        return x.next;
    }


    /**
     * 反转单项链表（https://leetcode.com/problems/reverse-linked-list/submissions/）
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode index = head;
        while (index != null) {//注意这个条件，不是 index.next
            ListNode next = index.next;
            index.next = pre;
            pre = index;
            index = next;
        }
        //注意这个返回
        return pre;
    }

    /**
     * 反转部分节点，1 ≤ m ≤ n ≤ length of list.
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        //首先遍历 n 次，使 mP、nP 分别指向 m、n 位置上的节点，preMP、nextNP 指向 m 前一个以及 n 后一个位置上的节点
        ListNode preMP = null, mP = null, nP = null, nextNP = null;
        ListNode index = head;
        int count = 1;
        while (count <= n) {
            if (count == m - 1) {
                preMP = index;
            }
            if (count == m) {
                mP = index;
            }
            if (count == n) {
                nP = index;
            }
            count++;
            index = index.next;
        }
        nextNP = nP.next;

        //反转 mP 到 nP
        index = mP;
        ListNode preNode = null;
        while (index != nextNP) {
            //【注意】这里是 index != nextNP，不是 index != np
            ListNode next = index.next;
            index.next = preNode;
            preNode = index;
            index = next;
        }

        //【难点】最后是这里，如果 m = 1，preMP 就是 null，这时候需要修改 head 指针
        if (preMP == null) {
            head = nP;
        }
        else {
            preMP.next = nP;
        }
        mP.next = nextNP;
        return head;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

}