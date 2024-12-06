package backend.academy;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import backend.academy.renderer.ParallelRenderer;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.nonLinear.DiscTransformation;
import backend.academy.transformation.nonLinear.ExponentialTransformation;
import backend.academy.transformation.nonLinear.EyefishTransformation;
import backend.academy.transformation.nonLinear.HandkerchiefTransformation;
import backend.academy.transformation.nonLinear.JuliaTransformation;
import backend.academy.transformation.nonLinear.PowerTransformation;
import backend.academy.transformation.nonLinear.SinusoidalTransformation;
import backend.academy.transformation.nonLinear.SphericalTransformation;
import backend.academy.transformation.nonLinear.SpiralTransformation;
import backend.academy.transformation.nonLinear.SwirlTransformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import static backend.academy.utils.ListUtils.getRandomElement;

@UtilityClass
public class FractalFlameGenerator {

    private static final List<Class<? extends Transformation>> CLASSES = List.of(
        DiscTransformation.class,
        ExponentialTransformation.class,
        EyefishTransformation.class,
        HandkerchiefTransformation.class,
        JuliaTransformation.class,
        PowerTransformation.class,
        SinusoidalTransformation.class,
        SphericalTransformation.class,
        SpiralTransformation.class,
        SwirlTransformation.class
    );

    @SneakyThrows
    public static FractalImage generateRandom(boolean symmetry) {
        var random = ThreadLocalRandom.current();
        List<Transformation> transformations = new ArrayList<>();
        int numberOfTransformations = random.nextInt(2, 7);
        int numberOfSamples = random.nextInt(3, 10);
        int symmetryNumber = (symmetry ? random.nextInt(2, 12) : 1);
        for (int i = 0; i < numberOfTransformations; i++) {
            var tClass = getRandomElement(CLASSES);
            transformations.add(tClass.newInstance());
        }

        var renderer = new ParallelRenderer(
            3840,
            2160,
            transformations
        );
        return renderer.render(
            Rectangle.getDefaultRectangle(),
            numberOfSamples,
            1000000,
            symmetryNumber,
            2
        );
    }
}
