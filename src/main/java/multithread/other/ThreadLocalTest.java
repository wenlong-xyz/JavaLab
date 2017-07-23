package multithread.other;

/**
 * Created by wenlong on 2017/4/11.
 */
public class ThreadLocalTest {
    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> count = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                count.set(count.get() + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                System.out.println(count.get());
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable sharedRunnableInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);
        thread1.start();
        thread2.start();
    }
}
