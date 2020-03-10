package com.zst.test.algorithm.stack_queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/17 上午8:24
 * @version: V1.0
 */
public class StackQuestion {

    /**
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * https://leetcode.com/problems/valid-parentheses/
     * 借助 Map 保存映射关系，key - 右括号，value 左括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>(3);
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');


        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            //这里原来用 map.keySet().contains(xx)，改成 containsKey(xx) 函数
            if(map.containsKey(s.charAt(i))) {
                //还要注意，不管是 peek() 还是 pop()，如果栈为空栈，都会抛出异常
                if(stack.isEmpty() || !map.get(s.charAt(i)).equals(stack.peek())){
                    return false;
                }
                else {
                    stack.pop();
                }
            }
            else {
                stack.add(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }


}