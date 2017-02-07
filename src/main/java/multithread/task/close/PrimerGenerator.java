package multithread.task.close;

/**
 * Created by zongw on 2017/2/7.
 */

public class PrimerGenerator implements Runnable {
    private volatile boolean cancelled;
    public void run() {
        while (!cancelled) {
            // do something
        }
    }
    public void cancle() {
        cancelled = true;
    }
}
