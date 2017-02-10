# ThreadPoolExecutor 线程池相关内容

## 使用经验
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
            + Caller-Runs 调用者优先， 该策略既不会抛弃任务，也不会抛弃异常，而是将某些任务会退到调用者。它不会在线程池的某个鲜橙汁执行新任务，而是在一个调用了execute的线程中执行该任务。当然如果新任务继续增多，最终会开始抛弃请求
            + 自定义：setRejectedExecutionHandler来修改ThreadPoolExecutor的饱和策略
    * 同步移交（Synchronous Handoff）： SynchronousQueue, 无排队任务，而是直接将任务移交给执行线程（如果没有空闲的执行线程，而且线程池数目达到最大数目，这个任务将被拒绝）
        - CachedThreadPool中采用SynchronousQueue，因此比CachedThreadPool有更好的派对性能，如果资源足够可以优选 CachedThreadPool
    * PriorityBlockingQueue: 优先级队列
4. **ThreadPoolExecutor 拓展方法**
    * 在子类中重写：beforeExecute, afterExecute 和terminated，拓展ThreadPoolExecutor功能，如增加日志输出等
    * 线程池关闭时调用terminated

## Reference：
1. 《Java并发编程实践》


