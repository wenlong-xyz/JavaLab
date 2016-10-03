package thread.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenlong on 2016/9/9.
 * 简单版读写锁
 */
public class ReadWriteLock1 {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;
    public synchronized void lockRead() throws InterruptedException{
        while(writers >0 || writeRequests > 0){
            wait();
        }
        readers ++;
    }
    public synchronized  void unlockRead(){
        readers --;
        notifyAll();
    }
    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        while(readers > 0 || writers > 0){
            wait();
        }
        writers++;
        writeRequests--;

    }
    public synchronized void unlockWrite() throws InterruptedException{
        writers--;
        notifyAll();
    }


}
