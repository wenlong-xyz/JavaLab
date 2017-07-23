package multithread.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zongw on 2017/2/5.
 */
public class BarrierTest {
    private final CyclicBarrier barrier;
    private final Worker[] workers;
    private volatile boolean endFlag = false;
    public BarrierTest(int count) {
        barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                long id = Thread.currentThread().getId();
                System.out.println(id + "All people are here, we should do something...");
                // maybe i will set the endFlag = true
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            this.workers[i] = new Worker();
        }
    }
    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!endFlag) {
                long id = Thread.currentThread().getId();
                // work
                System.out.println(id + "worker do something in current step");
                System.out.println(id + "work in current step is done");

                try {
                    // wait other worker finish their work
                    barrier.await();
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }
    }
    public void start() {
        for (Worker worker : workers) {
            new Thread(worker).start();
        }
    }

    public static void main(String[] args) {
        BarrierTest test = new BarrierTest(10);
        test.start();
    }
}
