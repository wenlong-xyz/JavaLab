package multithread.task.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zongw on 2017/2/5.
 */
public class FutureTest {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    public void renderPage(CharSequence source) {
        final List<String> imageUrls = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                // downlad images
                return null;
            }
        };
        Future<List<ImageData>> future = executor.submit(task);

        //renderText(source);


        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                // renderImage(data);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            // throw exception
        }
    }
    private List<String> scanForImageInfo(CharSequence source) {
        List<String> urls = new ArrayList<>();
        // do something
        return urls;
    }
}
class ImageData {

}
