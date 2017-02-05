package multithread.tools;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zongw on 2017/2/5.
 */
public class LatchTest {
    public long timeTasks(int threadNum, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNum);

        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            t.start();
        }
        long start = System.nanoTime();
        // start all threads
        startGate.countDown();
        // wait for all threads' ending
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
