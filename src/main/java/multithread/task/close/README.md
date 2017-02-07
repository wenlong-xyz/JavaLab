# 任务的取消与关闭 Task Cancel and Close

## 线程关闭
1. **使用volatile类型的域来保存取消状态**

    ```java
    public class PrimerGenerator implements Runnable {
        private volatile boolean cancelled;
        public void run() {
            while (!cancelled) {
                // do something for task
            }
        }
        public void cancle() {
            cancelled = true;
        }
    }
    ```
   * 调用cancel方法，即可以使得任务结束
   * 问题:
        - 退出过程需要花费一定的时间
        - 如果 `// do something for task` 是一个阻塞方法，那任务可能将永远不会检查取消表示，因此永远不会结束
2. **更好的方式：调用中断方法interrupt()**

    ```java
    public class Thread {
        // 终端目标线程
        public void interrupt() { ... }
        // 返回目标线程的中断状态
        public boolean isInterrupted() { ... }
        // 清除当前线程的中断状态(这点使用要小心)，并返回它之前的值
        public static boolean interrupted() { ... }
        ...
    }
    ```
    * 每个线程都有一个boolean类型的中断状态，线程中断时，中断状态被设置为true
    * Java 没有提供抢占式中断机制（Thread.stop，但问题很多），只提供协作式的中断机制，通过推迟中断请求的处理，开发人员能够制定更灵活的中断策略
    * 调用interrupt并不意味着立即停止目标线程正在进行的工作，而只是传递了请求中断的信息。由线程在下一个合适的时刻中断自己
    * 一般调用Thread.interrupt()会有两种处理方式
        - 遇到一个低优先级的block状态时，比如object.wait(),thread.sleep(),object.join()。它会立马触发一个unblock解除阻塞，并throw一个InterruptedException。
        - 其他情况，Thread.interrupt()仅仅只是更新了status标志位。然后你的工作线程通过Thread.isInterrrupted()进行检查，可以做相应的处理，比如也throw InterruptedException或者是清理状态，取消task等。
    * 阻塞库方法，例如thread.sleep和object.wait等，都会实现检查中断状态的操作
    * 不可中断的阻塞，不是所有可阻塞的方法都会提前返回或者抛出InterruptedException来响应中断请求，对于这些阻塞，需要重写包装interrupt，为其增加特定的操作，比如socket，利用close使其排除IOException
        - Java.io保重的同步Socket I/O
        - Java.io暴力的同步I/O
        - Selector的异步I/O
        - 获得某个锁

    ```java
    public class PrimeProducer extends Thread{
        private final BlockingDeque<BigInteger> queue;
        PrimeProducer(BlockingDeque<BigInteger> queue) {
            this.queue = queue;
        }
        public void run() {
            try {
                // 加速中断响应
                while (!Thread.currentThread().isInterrupted()) {
                    BigInteger p = BigInteger.ONE;
                    // 可以会阻塞，但阻塞后仍能响应中断
                    queue.put(p);
                }
            } catch (InterruptedException consumed) {
                // 允许线程退出
            }
        }

        public void cancel() {
            interrupt();
        }
    }
    ```

3. **通过Future实现取消 (本质也是中断)**

    ```java
    public static void timedRun(Runnable r, long timeout, timeUit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch(TimeoutException e) {
            // 接下来任务将被取消
        } catch (ExecutionException e) {
            // 如果在任务中抛出了异常，那么重新开始该异常
            throw lauderThrowable(e.getCause());
        } finally {
            // 如果任务正在运行，那么将被中断
            // 如果任务已经结束，那么执行取消操作不会带来任何影响
            task.cacel(true);
        }
    }
    ```

## 停止基于线程的服务
1. ExecutorService shutdown / shutdownNow(“粗暴”)
2. “毒丸”对象：特殊的标记位，加入队列中，当读取到结束对象时，终止任务

## JVM关闭
1. **“关闭钩子” Shutdown Hook**
    * 关闭钩子是通过Runtime.addShutdownHook注册的但尚未开始的线程
2. **尽可能少使用守护进程（Daemon Thread）**
    * 守护线程与普通线程的区别当JVM停止时，所有仍然存在的守护线程都将被抛弃——即不会执行finally代码块也不会执行栈回卷（抛出异常时，将暂停当前函数的执行，开始查找匹配的catch子句。首先检查throw本身是否在try块内部，如果是，检查与该try相关的catch子句，看是否可以处理该异常。如果不能处理，就退出当前函数，并且释放当前函数的内存并销毁局部对象，继续到上层的调用函数中查找，直到找到一个可以处理该异常的catch。这个过程称为栈回卷（stack unwinding）），而JVM只是直接退出
3. **避免使用终结器finalize**

## Reference：
1. 《Java并发编程实践》


