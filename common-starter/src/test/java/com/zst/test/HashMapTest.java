package com.zst.test;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/15 下午5:18
 * @version: V1.0
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        String dsa = map.put(null, "dsa");
        System.out.println(map);

        Hashtable<Integer, String> tableMap = new Hashtable<>();
        String put = tableMap.put(null, "");
        System.out.println(tableMap);
    }

}