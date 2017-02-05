package multithread.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Created by zongw on 2017/2/5.
 */
public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        Writer writer = new Writer(exchanger);
        Reader reader = new Reader(exchanger);
        new Thread(writer).start();
        new Thread(reader).start();
    }

}
class Writer implements Runnable{
    private List<Integer> list;
    private final Exchanger<List<Integer>> exchanger;
    public Writer(Exchanger<List<Integer>> exchanger) {
        this.exchanger = exchanger;
        list = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = 0, num = 0; i < 10; i++) {
            list.add(num++);
            list.add(num++);
            list.add(num++);
            list.add(num++);
            list.add(num++);
            try {
                list = exchanger.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Reader implements Runnable{
    private List<Integer> list;
    private final Exchanger<List<Integer>> exchanger;
    public Reader(Exchanger<List<Integer>> exchanger) {
        this.exchanger = exchanger;
        list = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = 0, num = 0; i < 10; i++) {
            while (list.size() > 0) {
                System.out.println(list.remove(0));
            }
            try {
                list = exchanger.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
