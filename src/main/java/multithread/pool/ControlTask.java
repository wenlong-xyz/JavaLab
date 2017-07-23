package multithread.pool;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

/**
 * Created by wenlong on 2017/3/5.
 */
public class ControlTask implements Callable<Long> {
    @Override
    public Long call() {
        int i = 0;
        while (i < 10) {
            i++;
            try {
                Thread.sleep(1000);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return (long)0;
    }
}
