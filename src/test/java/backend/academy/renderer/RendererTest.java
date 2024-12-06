package backend.academy.renderer;

import backend.academy.imageProcessor.ColorCorrection;
import backend.academy.model.Rectangle;
import backend.academy.transformation.nonLinear.DiscTransformation;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class RendererTest {

    @Test
    void renderDefault() {
        DefaultRenderer defaultRenderer = new DefaultRenderer(1000, 1000, List.of(new DiscTransformation()));
        var image = defaultRenderer.render(
            Rectangle.getDefaultRectangle(),
            10,
            10000,
            6
            , ColorCorrection.DEFAULT_GAMMA
        );
        assertThat(image).isNotNull();
    }

    @Test
    void renderParallel() {
        ParallelRenderer parallelRenderer = new ParallelRenderer(1000, 1000, List.of(new DiscTransformation()));
        var image = parallelRenderer.render(
            Rectangle.getDefaultRectangle(),
            10,
            10000,
            6
            , ColorCorrection.DEFAULT_GAMMA
        );
        assertThat(image).isNotNull();
    }
}
