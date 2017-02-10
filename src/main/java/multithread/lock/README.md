# Lock 锁

## ReentrantLock
1. **基本介绍**
    * ReentrantLock实现了Lock接口
    * 获取锁是和进入同步代码块有着相同的内存语义
    * 和synchronized一样，提供了可重入的加锁语义
    * ReentrantLock支持Lock接口中定义的所有获取所模式，与同步块相比为处理锁的不可用性问题提供了更高的灵活性
        - 模式：普通，轮寻，定时，可中断等

    ```java
        Lock lock = new ReentrantLock();
        ...
        lock.lock();
        try {
            // 更新对象状态
            // 捕获异常，在必要时恢复不变性条件
        } finally {
            // 防止异常退出，锁未被清除
            lock.unnlock
        }
    ```
2. **特色内容**
    * 锁获取模式
        * 轮寻锁与定时锁
            - 更加完善的错误恢复机制，对于内置锁(synchronized等)死锁发生时，恢复的唯一方法是重启程序。
            - 调用tryLock()方法，可以避免死锁发生，调用该方法，如果锁无法获取，则直接返回false
            - 也可以使用定时锁，使得程序提前结束
        * 可响应中断(lockInterrupted方法)

    ```java
        // 轮寻锁
        while(true) {
            if (lock.tryLock()) {
                try {
                    // ...
                } finally {
                    laock.unlock();
                }
            }
        }

        // 定时锁
        if (!lock.tryLock(timeout, unit) {
            return false;
        }
        try {
            // ...
        } finally {
            lock.unlcok();
        }

        // 可中断
        lock.lockInterruptibly();
        try {
            // ...
        } finally {
            lock.unlock();
        }
    ```
    * 公平性
        * ReentrantLock提供两种模式
            * 公平锁：默认，按照他们发出请求的顺序获得锁
            * 非公平锁：允许“插队”
        * 对比：
            + 非公平锁性能可能优于公平锁，以为优于“插队”的存在，可能导致新请求直接满足，无需唤醒被阻塞的“老”请求
            + “老”请求可能长时间得不到响应
        * 内置锁也不会提供确定的公平保证，但大多数情况下，基本的公平性保证已经足够了

3. **拓展：读写锁-ReadWriteLock**
    * 读读允许重入，带有写的请求不允许重入

    ```java
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock r = lock.readLock();
        Lock w = lock.writeLock();
    ```

##

## Reference：
1. 《Java并发编程实践》


