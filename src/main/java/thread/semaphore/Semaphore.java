package thread.semaphore;

/**
 * Created by wenlong on 2016/9/9.
 */
public class Semaphore {
    private boolean signal = false;
    public synchronized void take(){
        this.signal = true;
        this.notify();
    }
    public synchronized void release() throws InterruptedException{
        while(!this.signal)
            wait();
        this.signal = true;
    }

}
