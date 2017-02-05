package multithread.tools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by zongw on 2017/2/5.
 */
public class SemaphoreTest<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public SemaphoreTest(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.sem = new Semaphore(bound);
    }
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release();
            }
        }
    }
    public boolean remove(T o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            sem.release();
        }
        return wasRemoved;
    }
}
