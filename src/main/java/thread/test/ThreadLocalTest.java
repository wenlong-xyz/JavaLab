package thread.test;

import java.util.Random;

/**
 * Created by wenlong on 2016/8/31.
 */
public class ThreadLocalTest {
    public static class MyRunnable implements Runnable{
        private ThreadLocal<Integer> local = new ThreadLocal<>();

        @Override
        public void run() {
            Random random = new Random();
            local.set(random.nextInt());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(local.get());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
