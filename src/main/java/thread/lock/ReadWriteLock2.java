package thread.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenlong on 2016/9/9.
 * 读锁可重入,写锁可重入
 */
public class ReadWriteLock2 {
    private Map<Thread,Integer> readingThreads = new HashMap<Thread, Integer>();
    private int writers = 0;
    private int writeRequests = 0;
    private Thread writingThread = null;


    public synchronized void lockRead() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(!canGrantReadAccess((callingThread))){
            wait();
        }
        readingThreads.put(callingThread,getReadAccessCount(callingThread) + 1);
    }
    public synchronized  void unlockRead(){
        Thread callingThread = Thread.currentThread();
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount == 1){
            readingThreads.remove(callingThread);
        }
        else{
            readingThreads.put(callingThread,accessCount - 1);
        }
        notifyAll();
    }
    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)){
            wait();
        }
        while(readingThreads.size() > 0 || writers > 0){
            wait();
        }
        writingThread = callingThread;
        writers++;
        writeRequests--;

    }
    public synchronized void unlockWrite() throws InterruptedException{
        writers--;
        if(writers == 0){
            writingThread = null;
        }
        notifyAll();
    }

    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        return accessCount == null ? 0 : accessCount;
    }
    private boolean canGrantReadAccess(Thread callingThread){
        if(writers > 0) return false;
        if(isReader(callingThread)) return true;
        if(writeRequests > 0) return false;
        return true;
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        if(readingThreads.size() > 0) return false;
        if(writingThread == null) return true;
        if(!isWriter(callingThread)) return false;
        return true;
    }
    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }
}
