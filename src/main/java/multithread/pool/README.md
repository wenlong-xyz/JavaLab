# 线程池相关内容

## 基本概念
1. **适合场景**：任务类型相同并且相互独立
2. **线程池大小**
    * 计算密集型任务：N个CPU的系统上，线程池大小为N+1时，通常能实现最优的利用率
    * I/O操作较多时：可以适当增加线程数量，同时可以通过调节线程池大小观察CPU利用率来选取合适的线程数量
    * 常见参数与分类
        - 参数：
            + 基本大小：没有任务时线程池大小
            + 最大大小：同时活动的线程上限，工作队列满了，会创新新线程，直到最大大小
            + 存活时间：如果某个线程空闲时间超过了了空闲时间，则会被标记位可回收，如果此时线程数目超过基本大小，则该线程将被终止
        - FixedThreadPool：设置固定大小的线程池，设定基本大小和最大大小，而且创建的线程池不会超时
            + 无界队列：队列中等待的任务可以无限制增加
        - CachedThreadPool: 基本大小设置为0，最大大小设置为Integer.MAX_VALUE，超时时间为1分钟，线程池数据可以近似无限扩张，当需求降低时自动收缩
        - SingleThreadExecutor: 单线程
        - ScheduleThreadPool: 创建一个固定长度的线程池，而且可以延迟或定时的方式来执行任务
3. **线程池内部队列**
    * 无界队列: FixedThreadPool,SingleThreadExecutor,队列中的等待执行的任务可以无限制增加
    * 有界队列：控制资源消耗
        - 被填满后，启用饱和策略
            + Abort Policy 终止: 默认饱和策略，抛出RejectedExecutionException
            + Discard 抛弃：抛弃队列中下一个将被执行的任务，然后尝试重新提交新任务
            + Caller-Runs 调用者优先， 该策略既不会抛弃任务，也不会抛弃异常，而是将某些任务会退到调用者。它不会在线程池的某个线程执行新任务，而是在一个调用了execute的线程中执行该任务。当然如果新任务继续增多，最终会开始抛弃请求
            + 自定义：setRejectedExecutionHandler来修改ThreadPoolExecutor的饱和策略
    * 同步移交（Synchronous Handoff）： SynchronousQueue, 无排队任务，而是直接将任务移交给执行线程（如果没有空闲的执行线程，而且线程池数目达到最大数目，这个任务将被拒绝）
        - CachedThreadPool中采用SynchronousQueue，因此比CachedThreadPool有更好的派对性能，如果资源足够可以优选 CachedThreadPool
    * PriorityBlockingQueue: 优先级队列

## 使用示例
### 线程池主要工作流程
![](threadpool.jpg)
* [图片来源]( http://www.infoq.com/cn/articles/java-threadPool)

### 通过调用Executors的静态工厂方法创建
```java
public class SimpleThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}
```
1. **FixedThreadPool**
    * 大小固定
    * 每提交一个任务时就创建一个线程，知道达到线程池的最大数量，线程池的规模将不再变化
2. **CachedThreadPool**
    * 可缓存的线程池
    * 当线程池规模超过处理需求时，回收空闲线程；但需求增加时，则添加新的线程
3. **SingleThreadExecutor**
    * 单线程的Executor
4. **ScheduleThreadPool**
    * 创建一个固定长度的线程池，而且可以延迟或定时的方式来执行任务

## 通过ThreadPoolExecutor创建,定制线程池
```java
public class WorkerPool {

    public static void main(String args[]) throws InterruptedException{
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new ThreadPoolExecutor.AbortPolicy();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //submit work to the thread pool
        for(int i=0; i<10; i++){
            executorPool.execute(new WorkerThread("cmd"+i));
        }

        Thread.sleep(30000);
        //shut down the pool
        executorPool.shutdown();

    }
}
```

```java
new  ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds,runnableTaskQueue, handler);
```

* corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads方法，线程池会提前创建并启动所有基本线程。
* runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。 可以选择以下几个阻塞队列。
    - ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
    - LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
    - SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
    - PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
* maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
* ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
* RejectedExecutionHandler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。以下是JDK1.5提供的四种策略。
    - AbortPolicy：直接抛出异常。
    - CallerRunsPolicy：只用调用者所在线程来运行任务。
    - DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
    - DiscardPolicy：不处理，丢弃掉。
    - 当然也可以根据应用场景需要来实现RejectedExecutionHandler接口自定义策略。如记录日志或持久化不能处理的任务。
* keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
* TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），小时（HOURS），分钟（MINUTES），毫秒(MILLISECONDS)，微秒(MICROSECONDS, 千分之一毫秒)和毫微秒(NANOSECONDS, 千分之一微秒)。

* **ThreadPoolExecutor 拓展方法**
    * 在子类中重写：beforeExecute, afterExecute 和terminated，拓展ThreadPoolExecutor功能，如增加日志输出等
    * 线程池关闭时调用terminated

## Reference：
1. 《Java并发编程实践》
2. [聊聊并发（三）——JAVA线程池的分析和使用](http://www.infoq.com/cn/articles/java-threadPool)


