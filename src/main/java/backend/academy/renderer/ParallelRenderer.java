package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;

public class ParallelRenderer extends AbstractRenderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ParallelRenderer(int width, int height) {
        super(width, height);
    }

    @SneakyThrows
    @Override
    protected void processSamples(
        FractalImage fractalImage,
        Rectangle rectangle,
        int samples,
        int iterPerSample,
        int symmetry
    ) {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(50);
        }
    }
}
