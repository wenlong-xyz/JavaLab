# 任务执行 Task Execution

## 问题由来
1. **串行执行任务**

    ```java
    public class Server {
        public static void main(String[] args) throws IOException {
            ServerSocket socket = new ServerSpclet(80);
            while (true) {
                Socket connection = socket.accept();
                handleRequest(connection);
            }
        }
    }
    ```
   * 问题：单线程执行任务，效率低。无法提供高吞吐率或快速响应性
2. **一个任务一个线程**

    ```java
    public class Server {
        public static void main(String[] args) throws IOException {
            ServerSocket socket = new ServerSpclet(80);
            while (true) {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    @Override
                    public void run(){
                        handleRequest(connection);
                    }
                }
                new Thread(task).start();
            }
        }
    }
    ```
    * 优点：可以提高响应性和吞吐率
    * 缺点：当线程数量多到一定程度时，任务处理效率会下降。当线程数量过多，可能会耗尽系统资源，程序崩溃
        - 线程生命周期开销非常高：创建时的时间开销等
        - 资源消耗：内存等
        - 稳定性：过多的线程导致程序崩溃
3. **Executor框架-线程池**

    ```java
    public class Server {
        private static final int THREAD_NUM = 100;
        private static final Executor exec = Executors.newFixedThreadPool(THREAD_NUM);

        public static void main(String[] args) throws IOException {
            ServerSocket socket = new ServerSpclet(80);
            while (true) {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    @Override
                    public void run(){
                        handleRequest(connection);
                    }
                }
                exec.execute(task);
            }
        }
    }
    ```
    * 限定线程数目，控制系统资源消耗

## 线程池 Thread Pool
（以下线程池，可以通过调用Executors的静态工厂方法创建）

1. **FixedThreadPool**
    * 大小固定
    * 每提交一个任务时就创建一个线程，知道达到线程池的最大数量，线程池的规模将不再变化
2. **CacheedThreadPool**
    * 可缓存的线程池
    * 当线程池规模超过处理需求时，回收空闲线程；但需求增加时，则添加新的线程
3. **SingleThreadExecutor**
    * 单线程的Executor
4. **ScheduleThreadPool**
    * 创建一个固定长度的线程池，而且可以延迟或定时的方式来执行任务

## Executor 生命周期
可以通过ExecutorService来维护Executor生命周期

1. **运行**
    * execute
2. **关闭**
    * shutdown 方法，执行平缓的关闭过程：不再接受新的任务，同时等待已经提交的任务执行完成（报刊还未开始执行的任务）
    * shutdownNow 方法，执行粗暴的关闭过程：尝试取消所有运行中的任务，并且不在启动队列中尚未开始执行的任务
3. **已终止**
    * isTerminated 方法，判断是否终止（所有任务执行完，会进入终止状态）
    * awaitTermination方法，等待终止（阻塞直到所有任务执行完或超时或该线程被中断）

## 定时或周期任务
1. **Timer**
    * Timer执行定时任务时只会创建一个线程，如果某个任务执行时间过长，可能会破坏其他TimerTask的定时精准性
    * Timer线程不捕获异常
2. **ScheduledThreadPool**：
    * 比Timer更好，可以创建线程池
    * ScheduledThreadPool可以通过ScheduledThreadPoolExecutor搞糟函数或newScheduledThreadPool工厂方法创建
3. 为任务设定时限（超过时限，取消执行，客户端可以通过调用future.get(会抛出CancellationException)或者isCancelled方法判断是哪种情况）
    ```java
        ExecutorService executorService = Executors.newCachedThreadPool();
        // RequestTask implements Callable
        List<RequestTask> tasks = new ArrayList<>();
        executorService.invokeAll(tasks, time, unit);
    ```

## Future
### Future + Callable + ExecutorService
* Callable 接口中 call方法替代Runnable中的run的方法。
* 将Callable对象提交给ExecutorService执行，返回相应的Future对象
* Future.get(), 如果任务完成，get会立即返回或者抛出一个Exception，如果任务没有完成，get将阻塞并指导任务完成
* [示例代码](FutureTest.java) 加载网页文字和图片（一个线程加载文字，一个线程下载图片）

### CompletionService
* 将Executor和BlockingQueue的功能融合在一起
* 将Callable任务提交给它执行，然后使用类似队列操作的take和poll等方法获取已完成的结果（结果会被封装为Future）
* [示例代码](CompletionServiceTest.java) 将图片下载任务交由线程池完成，每下载完成一个，加载一个


## Reference：
1. 《Java并发编程实践》


