package multithread.tools;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zongw on 2017/2/10.
 */
public class AtomicLockCompare {
}
class ReetrantLockPseudoRandom {
    private final Lock lock = new ReentrantLock(false);
    private int seed;
    ReetrantLockPseudoRandom (int seed) {
        this.seed = seed;
    }
    public int nextInt(int n) {
        lock.lock();
        try {
            int s = seed;
            // seed = calculateNext(s);
            int remainder = s % n;
            return remainder > 0 ? remainder : remainder + n;
        } finally {
            lock.unlock();
        }
    }

}

class AtomicPseudoRandom {
    private AtomicInteger seed;
    AtomicPseudoRandom (int seed) {
        this.seed = new AtomicInteger(seed);
    }
    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = 0; // this line should be replaced by : int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

}

