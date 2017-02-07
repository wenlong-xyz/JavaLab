package multithread.task.close;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;

/**
 * Created by zongw on 2017/2/7.
 */
public class PrimeProducer extends Thread{
    private final BlockingDeque<BigInteger> queue;
    PrimeProducer(BlockingDeque<BigInteger> queue) {
        this.queue = queue;
    }
    public void run() {
        try {
            // 加速中断响应
            while (!Thread.currentThread().isInterrupted()) {
                BigInteger p = BigInteger.ONE;
                // 可以会阻塞，但阻塞后仍能响应中断
                queue.put(p);
            }
        } catch (InterruptedException consumed) {
            // 允许线程退出
        }
    }

    public void cancel() {
        interrupt();
    }
}
