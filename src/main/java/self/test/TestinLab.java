package self.test;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
import org.junit.Test;
import org.omg.CORBA.StringSeqHelper;
import sun.reflect.generics.tree.Tree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.util.Arrays.binarySearch;
import static javafx.scene.input.KeyCode.L;
import static self.test.TestinLab.SingletonEnum.INSTANCE;
import static self.test.TestinLab.State.START;
import static sun.java2d.cmm.ColorTransform.In;

/**
 * Created by wenlong on 2016/10/7.
 */
public class TestinLab {
    public static void main(String[] args) {
        StringBuffer s = new StringBuffer();
        new String(new String(""));
        s.append('d');
        s.deleteCharAt(0);
        boolean a;
        Set<Integer> set = new HashSet<>();
        set.add(1);

        for(int i = 0;i < 4;i++){
            System.out.println(i);
            continue;
        }
    }

    @Test
    public void arrayTest(){

        int[] from = new int[2];
        int[] to = Arrays.copyOf(from,from.length);
        String a;
        List<Integer>[] graph = new List[2];
        int[] array = {1,2,3,4,5};
        Arrays.sort(array);
        Arrays.asList(array);
        Arrays.copyOf(array,array.length);
        Arrays.copyOfRange(array, 1, array.length);
        Arrays.fill(array, 1);
    }

    private void listTest(){
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> source = new ArrayList<>(map.values());
        List<Integer> newList = new ArrayList<>();

        source.clear();
        newList.addAll(source);
        newList.toArray();
        newList.subList(1,2);
        LinkedList<Integer> list = new LinkedList<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(1);
        q.poll();

        q.poll();
        q.isEmpty();
        LinkedList<Integer> link = new LinkedList<>();
        link.getLast();
        link.getFirst();
        Iterator<Integer> it = link.iterator();
        it.remove();
        Collections.reverse(link);
        Collections.binarySearch(source,2);
        link = new LinkedList<>();
        link.isEmpty();
        link.removeLast();



//        link.addAll();
    }

    private void mapTest(){
        Map<Integer,Integer> map = new HashMap<>();
        map.getOrDefault(1,0);
        map.putIfAbsent(1,1);
        map.containsKey(1);
        map.put(null, 1);

//        map.put()
        map.values();
        map.get(null);
        for(Map.Entry<Integer,Integer> e : map.entrySet()){
            e.getKey();
            e.getValue();
        }
        TreeSet<Map.Entry<Integer,Integer>> set = new TreeSet<>(map.entrySet());

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.firstKey();
        treeMap.lowerKey(1);
        treeMap.floorKey(2);
        treeMap.ceilingKey(2);
        treeMap.higherKey(1);
        treeMap.lastKey();
        map.values();


        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.keySet().iterator().remove();


        // LRU
        int capacity = 22;
        Map<Integer,Integer> LRUMap = new LinkedHashMap<Integer, Integer>(16, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };

    }


    @Test
    public void stringTest(){
        int[][] a = new int[0][0];
        StringBuilder sb = new StringBuilder();
        char c = 'a' + 1;
        String str = " aa aa ";
        System.out.println(str.trim());
        System.out.println(str);
        System.out.println(Arrays.toString(str.split(" ")));
        sb = new StringBuilder();
        char [] strChar = str.toCharArray();
        String b = new String(strChar,1,1);
        char [] cs = new char[10];
        new String(cs);
        new String(cs,1,2);
        sb.append(1);

        sb.length();
        sb.append(cs, 1,2);
        System.out.println(sb.toString());
        // start, end
        sb.delete(1,2);
        sb.length();
        sb.insert(1,'2');
        // start, end
        sb.substring(1,2);
        sb.reverse();
        sb.setCharAt(1, '1');
        sb.deleteCharAt(2);

        String douhaos = ",,,2,,,";
        System.out.println(douhaos.substring(1));
        System.out.println(Arrays.toString(douhaos.split(",")));
        douhaos.indexOf("");
        sb.append(new StringBuffer()) ;
        douhaos.lastIndexOf('\t');
        douhaos.contains("2");

        sb.reverse();
        douhaos.indexOf('a',2);
        // start, end
        douhaos.substring(1,2);

        String byteStr = "1231aasdf我";
        System.out.println(byteStr.length());
        System.out.println(byteStr.getBytes().length);




    }

    @Test
    public void intTest(){

        long minusNum = (long) Math.pow(2,32);
        System.out.println(-1/5);
        System.out.println(Integer.MAX_VALUE);
        Integer.parseInt("123");
        Integer.parseInt("123");

        int a = -294967296;
        int b = 2000000000;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Long.toBinaryString((long)a - (long)b));
        System.out.println(Integer.toBinaryString(a - b));
        Integer integer = 2;
        integer.compareTo(3);
        Integer.valueOf("2");
        Integer.parseInt("2");
        Character.isDigit('1');
        Character.isUpperCase('a');
        Character.isLowerCase('a');

    }

    @Test
    public void priorityQueueTest(){
        Queue<Share> priorityQueue = new PriorityQueue<>(new Comparator<Share>() {
            @Override
            public int compare(Share o1, Share o2) {
                return o1.price - o2.price;
            }
        });
        priorityQueue.offer(new Share(1,22));
        priorityQueue.offer(new Share(2,33));

        System.out.println(priorityQueue.peek().index);
        priorityQueue.poll();
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.remove(2);

        Queue<Share> priorityQueue2 = new PriorityQueue<>(Collections.reverseOrder());

    }
    class Share implements Comparable<Share>{
        int index;
        int price;
        Share(int index, int price){
            this.index = index;
            this.price = price;
        }
        @Override
        public int compareTo(Share s){
            return 1;
        }
    }


    @Test
    public void printTest(){
        System.out.printf("");
        System.out.format("");
        String temp = String.format("aaa %d",2);
        System.out.println(temp);
    }

    @Test
    public void randomTest(){
        Random r = new Random();
        r.nextInt();
        r.nextInt(5);
        r.nextGaussian();
    }


    public void classTest(){
    }

    public void setTest(){
        Set<Integer> set = new HashSet<>();
        Iterator<Integer> it = set.iterator();
        int a = it.next();
        set.add(1);
        set.remove(1);
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.ceiling(1);
        treeSet.add(2);
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.iterator().next();

    }
    @Test
    public void setJTest(){
        Set<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);
        System.out.println(set.iterator().next());
        set.add(3);
        set.remove(1);
        set.add(1);
        System.out.println(set.iterator().next());
    }

    @Test
    public void stackTest(){
        Deque<Integer> stack = new ArrayDeque<>();
        stack.poll();
        stack.isEmpty();
        stack.pop();
        stack.removeLast();
    }

    public void compareTest(){
        List<String> list = new ArrayList<>();
        Collections.sort(list, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                return 0;
            }
        });
    }

    @Test
    public void scannerTest(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        Scanner in = new Scanner(System.in);
        in.nextInt();
        in.next();
    }

    public void bitSetTest(){
        BitSet  set = new BitSet(10);
        BigInteger bi = new BigInteger("123");
        bi.shiftLeft(2);
        bi.testBit(2);

        int a = 0;
        a ^= 1;
        Integer.lowestOneBit(a);
    }
    @Test
    public void modTest() {
        int a = -1;
        int b = 2;
        System.out.println(a % 2);
    }


    public void arraysTest(){
        int[] a = {1,2,3,4,5};
        Arrays.binarySearch(a, 2);
        // array, from, to, key
        Arrays.binarySearch(a,2,2,2);
        List<String>[] a2 = new List[2];
        int[][] aa = new int[2][2];
        Arrays.fill(a, 1);
        // array, length
        Arrays.copyOf(a,0);
        Arrays.copyOf(a,0);
        Arrays.asList(1,2,3);
//        System.arraycopy();
    }
    public void dequeTest() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.peekLast();
        deque.descendingIterator();
        deque.push(2);
        deque.iterator();
    }

    @Test
    public void mathTest() {
        Math.pow(1,2);
        Math.ceil(1);
        double aa = 10.2e2;
        aa = 10.2E2;
        aa = 10e2;
        aa = 10e0;
        aa = 10.2e-2;
        aa = 10.2e+2;
        aa = 10e+2;
        aa = .1;
        aa = +.1;
        aa = 1.;
        aa = 46.e3;

        System.out.println(3 / (-2));  // -1
        System.out.println(3 %(-2));   // 1
        System.out.println(-3 / (-4));  // 0
        System.out.println(-3 % (-4));  // -3

    }

    @Test
    public void timeTest(){
        String time = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        System.out.println(time);
    }
    public void automicTest() {
        AtomicInteger atomicInteger = new AtomicInteger(2);
        atomicInteger.incrementAndGet();
    }


    class Tweet implements Comparable<Tweet> {
        int tweetId;
        int userId;
        Date time;
        @Override
        public int compareTo(Tweet t) {
            return this.time.compareTo(t.time);
        }
    }
    enum State {
        START, END, ONE, TWO, THREE, FALSE, FIVE, SIX;
        public void execute (String arg) {
            // Perform operation here
        }
    }
    public void enumTest(){
        State state = State.START;
        switch (state) {
            case END:
                break;
        }
    }

    public enum SingletonEnum {
        INSTANCE;
        private final String[] favoriteSongs =
                { "Hound Dog", "Heartbreak Hotel" };
        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }
    }
    public void enumSingletonTest() {
        SingletonEnum singleton = INSTANCE;
        singleton.printFavorites();
    }

    public void atomicTest() {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
    }
    public void lockTest() {
        Lock lock = new ReentrantLock();
        lock.lock();
        Lock fairLock = new ReentrantLock(true);
        Lock unfairlock = new ReentrantLock();

    }

    public void innerClassTest() {
        InnerClass1 staticCalss = new InnerClass1();
        Inner.InnerStatic innerStatic = new Inner.InnerStatic();
    }
    private static String  name = "adfads";
    /**
     *静态内部类
     */
    static class InnerClass1{
        /* 在静态内部类中可以存在静态成员 */
        public static String _name1 = "chenssy_static";

        public void display(){
            /*
             * 静态内部类只能访问外围类的静态成员变量和方法
             * 不能访问外围类的非静态成员变量和方法
             */
            System.out.println("OutClass name :" + name);
        }
    }

    /**
     * 非静态内部类
     */
    class InnerClass2{
        /* 非静态内部类中不能存在静态成员 */
        public String _name2 = "chenssy_inner";
        /* 非静态内部类中可以调用外围类的任何成员,不管是静态的还是非静态的 */
        public void display(){
            System.out.println("OuterClass name：" + name);
        }
    }

}
class Inner {
    public static class InnerStatic{
        /* 在静态内部类中可以存在静态成员 */
        public static String _name1 = "chenssy_static";

        public void display(){
            /*
             * 静态内部类只能访问外围类的静态成员变量和方法
             * 不能访问外围类的非静态成员变量和方法
             */
            System.out.println("OutClass name :" );
        }
    }
}
interface  I1{}
interface  I2{}
interface  I3 extends I1, I2{}
