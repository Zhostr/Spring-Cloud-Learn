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
        while (slower != null && slower.next != null) {
            slower = slower.next;
            if (faster.next != null && faster.next.next != null) {
                faster = faster.next.next;
            }
            else {
                return false;
            }
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

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}