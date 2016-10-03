package thread.semaphore;

/**
 * Created by wenlong on 2016/9/9.
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();
        SendingThread sender = new SendingThread(semaphore);
        RecevingThread receiver = new RecevingThread(semaphore);
        receiver.start();
        sender.start();
    }
}

class SendingThread extends Thread{
    Semaphore semaphore = null;
    public SendingThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }
    public void run(){
        while(true){
            this.semaphore.take();
        }
    }
}
class RecevingThread extends Thread{
    Semaphore semaphore = null;
    public RecevingThread(Semaphore semaphore){
        this.semaphore= semaphore;
    }
    public void run() {
        while(true){
            try {
                this.semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}