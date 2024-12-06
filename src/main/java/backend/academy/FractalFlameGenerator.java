package backend.academy;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import backend.academy.renderer.ParallelRenderer;
import java.util.concurrent.ThreadLocalRandom;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalFlameGenerator {

    @SneakyThrows
    public static FractalImage generateRandom(boolean symmetryEnabled) {
        var random = ThreadLocalRandom.current();
        int numberOfSamples = random.nextInt(5, 15);
        int symmetry = symmetryEnabled ? random.nextInt(1, 12) : 1;

        var renderer = new ParallelRenderer(
            1920,
            1080
        );

        return renderer.render(
            Rectangle.getDefaultRectangle(),
            numberOfSamples,
            1_000,
            symmetry,
            2.2
        );
    }
}
