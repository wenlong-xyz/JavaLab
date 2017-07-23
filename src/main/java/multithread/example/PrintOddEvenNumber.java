package multithread.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wenlong on 2017/3/9.
 */
public class PrintOddEvenNumber {
    enum State {
        ODD, EVEN
    }
    private int odd;
    private int even;
    private int limit;
    private volatile State state;
    Lock lock = new ReentrantLock();
    Condition nextEven = lock.newCondition();
    Condition nextOdd = lock.newCondition();
    public PrintOddEvenNumber(int limit) {
        this.odd = 1;
        this.even = 2;
        this.limit = limit;
        this.state = State.ODD;
    }

    public void addOdd() throws InterruptedException {
        while (odd < limit) {
            lock.lock();
            try {
                if (state == State.EVEN) {
                    nextOdd.await();
                }
                state = State.EVEN;
                System.out.println(odd);
                odd += 2;
                nextEven.signal();
            } finally {
                lock.unlock();
            }
        }

    }
    public void addEven() throws InterruptedException {
        while (even < limit) {
            lock.lock();
            try {
                if (state == State.ODD) {
                    nextEven.await();
                }
                state = State.ODD;
                System.out.println(even);
                even += 2;
                nextOdd.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenNumber printer = new PrintOddEvenNumber(100);
        Thread oddThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printer.addOdd();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Thread evenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printer.addEven();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        oddThread.start();
        evenThread.start();
    }

}
