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
                System.out.println("All people are here, we should do something...");
                // maybe i will set the endFlag = true
            }
        });
        this.workers = new Worker[count];
    }
    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!endFlag) {
                // work
                System.out.println("worker do something in current step");
                System.out.println("work in current step is done");

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
}
