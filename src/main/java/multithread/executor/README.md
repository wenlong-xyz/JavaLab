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


## Reference：
1. 《Java并发编程实践》


