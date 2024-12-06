package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.affine.AffineTransformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;

public class ParallelRenderer extends AbstractRenderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ParallelRenderer(int width, int height, List<Transformation> variations) {
        super(width, height, variations);
    }

    @SneakyThrows
    @Override
    protected void processSamples(
        FractalImage fractalImage,
        Rectangle rectangle,
        List<AffineTransformation> affineTransformations,
        int samples,
        int iterPerSample,
        int symmetry
    ) {
        for (int i = 0; i < samples; i++) {
            executorService.execute(
                () -> processSample(fractalImage, rectangle, affineTransformations, iterPerSample, symmetry)
            );
        }
        executorService.close();
    }
}
