package backend.academy.renderer;

import backend.academy.imageProcessor.ColorCorrection;
import backend.academy.model.AffineTransformationCoefficients;
import backend.academy.model.Color;
import backend.academy.model.FractalImage;
import backend.academy.model.Pixel;
import backend.academy.model.Point;
import backend.academy.model.Rectangle;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.affine.AffineTransformation;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static backend.academy.utils.ListUtils.getRandomElement;
import static java.lang.Math.PI;

@Log4j2()
@RequiredArgsConstructor
public abstract class AbstractRenderer implements Renderer {

    private static final int START = -20;

    private final int width;

    private final int height;

    private final List<Transformation> variations;

    @Override
    public FractalImage render(
        Rectangle world,
        int samples,
        int iterPerSample,
        int symmetry,
        double gammaCorrection
    ) {
        FractalImage fractalImage = FractalImage.create(width, height);
        var affineTransformations = createAffineTransformations(samples);
        log.info("started");
        processSamples(fractalImage, world, affineTransformations, samples, iterPerSample, symmetry);
        log.info("rendered");
        new ColorCorrection(gammaCorrection).process(fractalImage);
        log.info("gamma corrected");
        return fractalImage;
    }

    protected abstract void processSamples(
        FractalImage fractalImage,
        Rectangle rectangle,
        List<AffineTransformation> affineTransformations,
        int samples,
        int iterPerSample,
        int symmetry
    );

    protected void processSample(
        FractalImage image,
        Rectangle world,
        List<AffineTransformation> affineTransformations,
        int iterPerSample,
        int symmetry
    ) {
        Point point = world.createRandomPoint();
        for (int i = START; i < iterPerSample; i++) {
            var affineTransformation = getRandomElement(affineTransformations);
            var nonLinearTransformation = getRandomElement(variations);
            point = affineTransformation.apply(point);
            point = nonLinearTransformation.apply(point);
            if (i > 0) {
                double phi = 0;
                for (int sector = 0; sector < symmetry; sector++) {
                    Point sectorPoint = point.rotatedBy(phi);
                    if (world.contains(sectorPoint)) {
                        int pixelX = (int) ((sectorPoint.x() - world.x()) * image.width() / world.width());
                        int pixelY = (int) ((sectorPoint.y() - world.y()) * image.height() / world.height());
                        if (image.contains(pixelX, pixelY)) {
                            Pixel pixel = image.data()[pixelY][pixelX];
                            synchronized (pixel) {
                                pixel.color().mix(affineTransformation.color());
                            }
                        }
                    }
                    phi += 2 * PI / symmetry;
                }
            }

        }
    }

    protected static List<AffineTransformation> createAffineTransformations(int n) {
        var affineTransformations = new ArrayList<AffineTransformation>();
        for (int i = 0; i < n; i++) {
            affineTransformations.add(new AffineTransformation(
                AffineTransformationCoefficients.create(),
                Color.createRandomColor()
            ));
        }
        return affineTransformations;
    }
}
