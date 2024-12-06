package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public abstract class AbstractRenderer implements Renderer {

    private static final int START = -20;

    private final int width;
    private final int height;

    @Override
    public FractalImage render(
        Rectangle world,
        int samples,
        int iterPerSample,
        int symmetry,
        double gammaCorrection
    ) {
        FractalImage fractalImage = FractalImage.create(width, height);
        log.info("started");
        processSamples(fractalImage, world, samples, iterPerSample, symmetry);
        log.info("rendered");
        return fractalImage;
    }

    protected abstract void processSamples(
        FractalImage fractalImage,
        Rectangle rectangle,
        int samples,
        int iterPerSample,
        int symmetry
    );
}
