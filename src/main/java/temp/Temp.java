package temp;

import java.util.Scanner;

/**
 * Created by wenlong on 2016/10/1.
 */

public class Temp {
    public Temp(){
        System.out.println("adfdasf");
    }
    public static void main(String[] args) {
        Temp[] temps = new Temp[100];
    }

}
class SingleList {
    ListNode head;
    class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    SingleList() {
        head = null;
    }
    public void insert(int[] vals) {
        if (vals.length > 0) {
            int start = 0;
            if (head == null) {
                head = new ListNode(vals[start++]);
            }
            ListNode cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            while (start < vals.length) {
                cur.next = new ListNode(vals[start++]);
                cur = cur.next;
            }
        }
    }
    public boolean insert(int pos, int val) {
        if (pos < 0) {
            return false;
        }
        if (pos == 0) {
            ListNode node = new ListNode(val);
            node.next = head;
            head = node;
            return true;
        }

        ListNode cur = head;
        for (int i = 1; i < pos && cur != null; i++) {
            cur = cur.next;
        }
        if (cur != null) {
            ListNode node = new ListNode(val);
            node.next = cur.next;
            cur.next = node;
            return true;
        }
        return false;
    }
    public boolean delete(int pos) {
        if (pos < 0) {
            return false;
        }
        if (pos == 0) {
            if (head == null) {
                return false;
            }
            head = head.next;
            return true;
        }
        ListNode cur = head;
        for (int i = 1; i < pos && cur != null; i++) {
            cur = cur.next;
        }
        if (cur != null && cur.next != null) {
            cur.next = cur.next.next;
            return true;
        }
        return false;
    }
    public void print() {
        ListNode cur = head;
        if (cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }
        while (cur != null) {
            System.out.print(" " + cur.val);
            cur = cur.next;
        }
        System.out.println("");
    }

}

interface I1{
    void f();
}
interface I2{
    void f(int i);
}
interface I3{
    int f();
}
interface I4{
    void f();
}
class C{
    public int f(){
        System.out.println("c.f");
        return 1;
    }
}

class C2 implements I1,I4{

    @Override
    public void f() {

    }


}

