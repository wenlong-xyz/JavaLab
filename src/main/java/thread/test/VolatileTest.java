package thread.test;

/**
 * Created by wenlong on 2016/9/2.
 */
public class VolatileTest {
    private static boolean stop;

    public static void main(String[] args) throws InterruptedException {
        stop = false;
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stop){
                    System.out.println("123");
                }
            }
        });
        background.start();
        Thread.sleep(1000);
        stop = true;
    }
}
