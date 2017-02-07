package multithread.task.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zongw on 2017/2/6.
 */
public class CompletionServiceTest {
    public class FutureTest {
        private final ExecutorService executor = Executors.newCachedThreadPool();
        public void renderPage(CharSequence source) {
            final List<String> imageUrls = scanForImageInfo(source);
            CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
            for (final String url : imageUrls) {
                completionService.submit(new Callable<ImageData>() {
                    @Override
                    public ImageData call() throws Exception {
                        // downlad images
                        return null;
                    }
                });
            }

            //renderText(source);

            try {
                for (int i = 0; i < imageUrls.size(); i++) {
                    // 任务完成时，计算结果会放入BlockingQueue中
                    Future<ImageData> f = completionService.take();
                    ImageData imageData = f.get();
                    // renderImage(data);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
}
