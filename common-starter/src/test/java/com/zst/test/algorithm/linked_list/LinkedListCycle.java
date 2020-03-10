package com.zst.test.algorithm.linked_list;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/12 上午10:45
 * @version: V1.0
 */
public class LinkedListCycle {

    /**
     * https://leetcode.com/problems/linked-list-cycle/
     * 判断链表是否有环
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode slower = head;
        ListNode faster = head;
        //slower != null，校验 head 不为空
        while (slower != null && faster != null && faster.next != null) {
            slower = slower.next;
            faster = faster.next.next;
            if(faster == slower) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO https://leetcode.com/problems/linked-list-cycle-ii/description/
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        return null;
    }

    /**
     * 环形单链表的约瑟夫问题
     * @param head
     * @param m 轮流报数，第 m 个就自杀
     * @return
     */
    public ListNode josephKill(ListNode head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        //第一步，先找到 head 的前一个节点
        ListNode preHead = head;
        while (preHead.next != head) {
            preHead = preHead.next;
        }

        //第二步，边遍历边删除节点
        int count = 1;
        while (preHead != head) {
            if (count == m) {
                preHead.next = head.next;
                head = head.next;
                count = 1;
            }
            else {
                preHead = head;
                head = head.next;
                count ++;
            }
        }
        return head;
    }

    /**
     * https://leetcode.com/problems/intersection-of-two-linked-lists/submissions/
     * 判断两个链表的相交节点（不能改变原链表）
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int sizeA = size(headA);
        int sizeB = size(headB);

        ListNode aHead = headA;
        ListNode bHead = headB;
        if (sizeA > sizeB) {
            for (int i = 0; i < sizeA - sizeB; i++) {
                aHead = aHead.next;
            }
        }
        else if(sizeA < sizeB) {
            for (int i = 0; i < sizeB - sizeA; i++) {
                bHead = bHead.next;
            }
        }

        while (aHead != null && bHead != null) {
            //注意这里是 aHead == bHead，不是 val 相等
            if (aHead == bHead) {
                return aHead;
            }
            aHead = aHead.next;
            bHead = bHead.next;
        }
        return null;
    }

    int size(ListNode node) {
        int result = 0;

        while (node != null) {
            result ++;
            node = node.next;
        }
        return result;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}