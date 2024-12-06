package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultRenderer extends AbstractRenderer {

    public DefaultRenderer(int width, int height) {
        super(width, height);
    }

    @Override
    protected void processSamples(
        FractalImage fractalImage,
        Rectangle rectangle,
        int samples,
        int iterPerSample,
        int symmetry
    ) {

    }
}
