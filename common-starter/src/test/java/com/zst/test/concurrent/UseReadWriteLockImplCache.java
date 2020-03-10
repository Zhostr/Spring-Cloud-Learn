package com.zst.test.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 使用读写锁实现缓存
 * @author: Zhoust
 * @date: 2020/02/02 下午7:28
 * @version: V1.0
 */
public class UseReadWriteLockImplCache<K, V> {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock read = readWriteLock.readLock();

    private Lock write = readWriteLock.writeLock();

    private Map<K, V> map = new HashMap<>();

    public V get(K key) {
        read.lock();
        try {
            return map.get(key);
        } finally {
            read.unlock();
        }
    }

    /**
     * 如果不存在某个 key，就从其他地方获取（假设查 DB），懒加载
     * @param key
     * @return
     */
    public V getIfAbsent(K key) {
        V result = null;
        read.lock();
        try {
            result = map.get(key);
        } finally {
            read.unlock();
        }
        if (null != result) {
            return result;
        }

        write.lock();
        try {
            //这里防止并发，需要再次执行一遍查询操作
            //多个线程同时执行 write.lock() 只有一个线程获得了锁，并执行完了后续操作，此线程已经填充了 key 对应的 value，其他线程无需再次查询
            result = map.get(key);
            if (null == result) {
                //result = xxx，假设从 DB 查询
                map.put(key, result);
            }
        } finally {
            write.unlock();
        }
        return result;
    }

    public V set(K key, V value) {
        write.lock();
        try {
            return map.put(key, value);
        } finally {
            write.unlock();
        }
    }

}