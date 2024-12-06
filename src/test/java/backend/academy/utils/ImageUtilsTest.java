package backend.academy.utils;

import backend.academy.format.ImageFormat;
import backend.academy.model.FractalImage;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

class ImageUtilsTest {

    @Test
    void saveFractalFlame(@TempDir Path path) {
        FractalImage image = FractalImage.create(10, 10);
        Path created = ImageUtils.saveFractalFlame(image, path.resolve("testFractal.png"), ImageFormat.PNG);
        Path expected = path.resolve("testFractal.png");
        assertThat(created.toFile().exists()).isTrue();
        assertThat(created).isEqualTo(expected);
    }
}
