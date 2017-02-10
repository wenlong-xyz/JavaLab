# Java 同步工具类

## 信号量 Semaphore
1. **基本概念**： Semaphore中管理着一组许可，许可的初始数量可以通过构造函数指定，执行操作前时首先需要获得（acquire）许可（如果还有剩余），并在使用后释放（release）许可，如果没有许可，那么acquire将阻塞知道许可
2. **实现类**：Semaphore
3. **应用场景示例**
    * 实现资源池
4. [示例代码](SemaphoreTest.java)：有界集合BoundedHashSet

## 闭锁 Latch
1. **基本概念**：相当于一扇门，闭锁到达结束状态前，门一直关着，所有线程都不能通过。当到达结束状态时，门打开并允许所有线程通过。并且保持结束状态不再改变
2. **实现类**： CountDownLatch
3. **应用场景示例**：
    * 确保某个计算在其需要的所有资源都被初始化后才继续执行
    * 确保某个服务在其依赖的所有其他服务都启动后才启动
    * 等待直到某个操作的所有参与者都就绪再继续执行
4. [示例代码](LatchTest.java)：控制所有子线程同时开始执行，统计所有线程执行时间（从开始到最后一个执行完）

## 栅栏 Barrier
1. **基本概念**
    * 类似于闭锁， 能阻塞一组线程直到某个事件发生
    * 与闭锁的区别在于，所有线程必须同时到达栅栏位置，才能继续执行。
    * 闭锁用于等待时间，而栅栏用于等待其他线程，例如：几个家庭决定在某个地方结合：“所有人下午6点在麦当劳碰头，到了以后要等其它人，都到齐后再讨论下一步要做的事”
2. **实现类**
    * CyclicBarrier
        - 将一个问题拆解为一系列相对独立的子问题
        - 当线程到达栅栏位置时，调用await方法，这个方法将阻塞知道所有线程都到达栅栏位置
        - 所有线程都到达栅栏位置后，栅栏打开，所有线程都被释放，栅栏被重置以便下次使用
        - 如果对await调用超时，或者await阻塞被中断，那么栅栏被打破，所有阻塞await的调用线程都会终止并抛出BrokenBarrierException
        - CyclicBarrier可以将一个Runnable传给构造函数，如果成功通过栅栏，传入的Runnable将被执行（最后一个到达的线程）
        - [示例代码](BarrierTest.java)
    * Exchanger
        - 双方（Two-Party）栅栏，双方在栅栏位置上交换数据。即两个线程之间在栅栏处进行数据交换
        - 比如一个线程向写缓冲区写数据，一个从读缓冲区读数据，当读缓冲区为空，写缓冲区为满时交换两个缓冲区
        - [示例代码](ExchangerTest.java)


## FutureTask + Callable
* Callable 接口中 call方法替代Runnable中的run的方法。
* 将Callable对象提交给Executor

## 自定义同步工具
1. **等待 wait**
    * 状态依赖方法的标准形式: 如有界队列等

    ```java
        void stateDependentMethod() throws InterruptedException {
            // 必须通过一个锁来保护条件为题
            synchronized(lock) {
                // 条件谓词 - 如: 队列满了，不能插入
                // 防止因线程竞争，状态被同时唤醒的其他线程改变
                // 防止信号丢失的问题。丢失的信号是指，线程必须等待一个已经为真的条件，但在开始等待之前没有检查条件谓词
                while (!conditionPredicte()) {
                    lock.wait();
                }
                // 现在对象处于合适状态，do something
            }
    ```

2. **唤醒 notify**
    * 两个基本概念
        - 锁池:假设线程A已经拥有了某个对象(注意:不是类)的锁，而其它的线程想要调用这个对象的某个synchronized方法(或者synchronized块)，由于这些线程在进入对象的synchronized方法之前必须先获得该对象的锁的拥有权，但是该对象的锁目前正被线程A拥有，所以这些线程就进入了该对象的锁池中。
        - 等待池:假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁后，进入到了该对象的等待池中
    * notify vs notifyAll
        - 如果线程调用了对象的 wait()方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
        - 当有线程调用了对象的 notifyAll()方法（唤醒所有 wait 线程）或 notify()方法（只随机唤醒一个 wait 线程），被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。也就是说，调用了notify后只要一个线程会由等待池进入锁池，而notifyAll会将该对象等待池内的所有线程移动到锁池中，等待锁竞争
        - 优先级高的线程竞争到对象锁的概率大，假若某线程没有竞争到该对象锁，它还会留在锁池中，唯有线程再次调用 wait()方法，它才会重新回到等待池中。而竞争到对象锁的线程则继续往下执行，直到执行完了 synchronized 代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。
        - 综上，所谓唤醒线程，另一种解释可以说是将线程由等待池移动到锁池，notifyAll调用后，会将全部线程由等待池移到锁池，然后参与锁的竞争，竞争成功则继续执行，如果不成功则留在锁池等待锁被释放后再次参与竞争。而notify只会唤醒一个线程。
    * 适用情况
        * notify: 条件队列上选择一个唤醒，可能会造成死锁。使用notify，而不是notifyAll的两个条件。
            - 所有等待线程的类型都相同：只有一个条件为此与条件队列相关，并且wait返回后执行相同的操作
            - 单进单出：在条件变量的每次通知，最多只能唤醒一个线程来执行
        * notifyAll：唤醒条件队列上等待的所有线程，然后再竞争。通常情况下优先使用notifyAll

3. **Condition**
    * 一种广义的内置条件队列
    * 通过添加多个条件队列，可以更好的控制线程的阻塞与唤醒

    ```java
    public class ConditionBoundedBuffer<T> {
        private  final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public void put(T x) throws InterruptedException {
            lock.lock();
            try {
                while (isFull()) {
                    notFull.await();
                }
                // put elements
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public T take () throws InterruptedException {
            lock.lock();
            try {
                while (isEmpty()) {
                    notEmpty.await();
                }
                // get elements
                notFull.signal();
                return e;
            } finally {
                lock.unlock();
            }
        }
    }
    ```
    * Condition中与wait, notify, notifyAll 对应的事await, signal, signalAll, 使用时注意
    * 通过Condition可以在每个锁上实现多个可中断、不可中断、基于时限的等待，以及公平的或者非公平的操作

4. **基于AbstractQueuedSynchronizer构建**
    * 同步器类中的AQS：ReentrantLock、Semaphore、ReentrantReadWriteLock、CountDownLatch、SynchronousQueue、FutrueTask等都是基于AQS构建的
    * 基本构建思路：利用AQS内置的state以及重写tryAcquire/tryRelease等方法控制同步器是否可以独占等
    * 以Semaphore为例：

    ```java
    protected int tryAcquiredShared(int acquires) {
        while (true) {
            int available = getState();
            int remaining = available - acquires;
            if (remaining < 0 || compareAndSetState(available, remaining)) {
                return remaining;
            }
        }
     }
    protected boolean tryReleaseShared(int releases) {
        while (true) {
            int p = getState();
            if (compareAndSetState(available, p + releases)) {
                return true;
            }
        }
    }

    ```

## 硬件对并发的支持 CAS Compare-and-Swap
1. **基本流程**
    * 读取读写的内存位置V
    * 进行比较的值A
    * 满足条件则写入新值B，返回比较前内存位置V的数值
2. **JVM对CAS的支持——原子变量类**
    * 标量类 Scalar: AtomicInteger/AtomicLong/AtomicBoolean/AtomicReference
        - 支持CAS，AtomicInteger/AtomicLong支持算数运算
        - 未重新定义hashCode或equals方法，不适合做散列表中key
    * 数组类: AtomicIntegerArray/AtomicLongArray/AtomicReferenceArray
        - 原子数组中的元素可以实现原子更新（volatile语义）
    * 更新器类
    * 复合变量类
3. 一般情况下，原子变量类性能优于锁（虽然使用轮询方式），当竞争程度达到较高时，锁的性能才会优于原子变量类
    * [对比示例代码](AtomicLockCompare.java)

## 非阻塞算法
1. **相关概念**
    * 非阻塞算法：如果在某种算法中，一个线程失败或者挂起不会导致其他线程也失败或挂起，这种算法就被称为非阻塞算法
    * 无锁算法 Lock-Free: 如果算法的每个步骤都能在存活的线程中执行下去，这种算法被称为无锁算法
    * 如果仅将CAS用于协调线程之间的操作，并且能正确实现，那它既是一种无阻塞算法，又是一种无锁算法
2. **构建技巧**
    * 将执行原子修改的范围缩小到单个变量上
    * 非阻塞的栈：CAS

    ```java
    public oid push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    ```

    * 非阻塞的链表
        - 技巧1：既是在一个包含多个步骤的更新操作中，也要确保数据结构总是处于一致状态
        - 技巧2：如果线程B到达时，发现A正在修改数据结构，那么在数据结构中应该保持足够的信息，使得B能完成A的更新操作，然后B继续它的事情

    ```java
    while (true) {
        Node<E> curTail = tail.get();
        Node<E> tailNext = curTail.next.get();
        if (curTail == tail.get()) {
            if (tailNext != null) {
                // 对列处于中间状态，推进为节点，即有其他线程在操作队列，本线程要帮他们“干点活”
                tail.compareAndSet(curTail, tailNext);
            } else {
                // 处于稳定状态，尝试插入新节点
                if (curTai.next.compareAndSet(null, newNode) {
                    // 尝试插入成功, 尝试推进尾节点
                    tail.compareAndSet(curTail, newNode);
                    return true;
                }
        }
    }
    ```
3. **ABA问题**
    * 如果A的值变为B，又B变回A，那么仍然被认为发生了变化，并需要重新执行算法红的某些步骤，这种情况下需要修改普通的CAS算法
    * 引入版本号，AtomicStampedReference(以及AtomicMarkableReference)支持在两个变量上执行原子的条件更新


## References：
1. 《Java并发编程实践》
2. [java中的锁池和等待池](http://blog.csdn.net/emailed/article/details/4689220)
3. [线程间协作：wait、notify、notifyAll](http://wiki.jikexueyuan.com/project/java-concurrency/collaboration-between-threads.html)


