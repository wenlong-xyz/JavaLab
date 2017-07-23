package multithread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenlong on 2017/3/5.
 */
public class ThreadPoolTest {
    public void construct() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new ControlTask());
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadPoolTest test = new ThreadPoolTest();
        System.out.println("construct start");
        test.construct();
        System.out.println("construct back");
    }
}
