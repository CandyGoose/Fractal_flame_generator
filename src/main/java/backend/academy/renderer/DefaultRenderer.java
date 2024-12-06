package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.affine.AffineTransformation;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultRenderer extends AbstractRenderer {

    public DefaultRenderer(int width, int height, List<Transformation> variations) {
        super(width, height, variations);
    }

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
            log.info("started sample");
            processSample(fractalImage, rectangle, affineTransformations, iterPerSample, symmetry);
        }
    }
}
