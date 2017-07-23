package self.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wenlong on 2017/1/17.
 */
public class AllOne {
    class Node {
        int val;
        Node pre;
        Node next;
        Set<String> keys;
        public Node(int val) {
            this.val = val;
            keys = new HashSet<>();
        }
    }
    private Node head;
    private Node tail;
    private Map<String, Node> keyNode;

    /** Initialize your data structure here. */
    public AllOne() {
        head = new Node(0);
        tail = new Node(Integer.MAX_VALUE);
        head.next = tail;
        tail.pre = head;
        keyNode = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        Node cur = keyNode.get(key);
        if (cur == null) {
            Node next = head.next;
            if (next.val == 1) {
                next.keys.add(key);
                keyNode.put(key, next);
            } else {
                cur = new Node(1);
                cur.keys.add(key);
                cur.pre = head;
                cur.next = next;
                head.next = cur;
                next.pre = cur;
                keyNode.put(key, cur);
            }
        } else {
            cur.keys.remove(key);
            Node next = cur.next;
            if (next.val == cur.val + 1) {
                next.keys.add(key);
                keyNode.put(key, next);
            }
            else {
                Node newNode = new Node(cur.val + 1);
                newNode.keys.add(key);
                cur.next = newNode;
                next.pre = newNode;
                newNode.next = next;
                newNode.pre = cur;
                keyNode.put(key, newNode);
                next = newNode;
            }

            if (cur.keys.isEmpty() && cur.val != Integer.MAX_VALUE) {
                Node pre = cur.pre;
                pre.next = next;
                next.pre = pre;
            }
        }

    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        Node cur = keyNode.get(key);
        if (cur != null) {
            cur.keys.remove(key);
            Node pre = cur.pre;
            Node next = cur.next;
            if (cur.val > 1) {
                if (pre.val + 1 == cur.val) {
                    pre.keys.add(key);
                    keyNode.put(key, pre);
                } else {
                    Node newNode = new Node(cur.val - 1);
                    newNode.keys.add(key);
                    cur.pre = newNode;
                    pre.next = newNode;
                    newNode.next = cur;
                    newNode.pre = pre;
                    keyNode.put(key, newNode);
                    pre = newNode;
                }
            } else {
                keyNode.remove(key);
            }

            if (cur.keys.isEmpty() && cur.val != Integer.MAX_VALUE) {
                pre.next = next;
                next.pre = pre;
            }
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.val + " " + cur.keys.size() + " " + cur.keys);
            cur = cur.next;
        }
        if (!tail.keys.isEmpty()) {
            return tail.keys.iterator().next();
        } else {
            Node pre = tail.pre;
            if (pre.val != 0) {
                return pre.keys.iterator().next();
            } else {
                return "";
            }
        }
    }
    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        Node next = head.next;
        if (!next.keys.isEmpty()) {
            return next.keys.iterator().next();
        } else {
            return "";
        }

    }
    public static void main(String[] args) {
        AllOne obj = new AllOne();
        obj.inc("hello");
        obj.dec("hello");
        obj.inc("hello");
        obj.dec("hello");
        System.out.println(obj.getMaxKey());
        System.out.println(obj.getMinKey());
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
