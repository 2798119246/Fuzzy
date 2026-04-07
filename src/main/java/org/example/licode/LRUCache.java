package org.example.licode;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    // 缓存表
    private final Map<Integer, LinkedNode> cache = new HashMap<>();
    // 当前缓存容量
    private int size;
    // 缓存容量
    private final int capacity;
    // 虚拟头节点
    private final LinkedNode dummyHead;
    // 虚拟尾节点
    private final LinkedNode dummyTail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        dummyHead = new LinkedNode();
        dummyTail = new LinkedNode();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    public int get(int key) {
        LinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        LinkedNode node = cache.get(key);
        if (node == null) {
            // 节点不存在则新建
            LinkedNode newNode = new LinkedNode(key, value);
            cache.put(key, newNode);
            // 把节点插入头节点后面
            addToHead(newNode);
            size++;
            // 超过缓存大小即把最后一个缓存去除掉
            if (size > capacity) {
                LinkedNode deleteNode = dummyTail.prev;
                // 移除末尾节点
                removeNode(deleteNode);
                cache.remove(deleteNode.key);
                size--;
            }
        } else {
            node.value = value;
            // 移到头部
            moveToHead(node);
        }

    }

    // 移除节点，其实就是把节点的前后节点互相修改前后指针，中间节点自然就相当于消失了
    private void removeNode(LinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 把接待你添加到头节点，添加可比移除麻烦多了
    private void addToHead(LinkedNode node) {
        // 创建新节点的前后指针
        node.next = dummyHead.next;
        node.prev = dummyHead;
        // 把原头节点和后继节点的 next和pre指向node
        dummyHead.next.prev = node;
        dummyHead.next = node;
    }

    //把某个节点挪到头接节点
    private void moveToHead(LinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    static class LinkedNode {
        int key;
        int value;
        LinkedNode prev;
        LinkedNode next;

        LinkedNode() {
        }

        LinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */