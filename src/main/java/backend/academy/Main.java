package backend.academy;

import backend.academy.format.ImageFormat;
import backend.academy.utils.ImageUtils;
import java.nio.file.Path;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        var fractalImage = FractalFlameGenerator.generateRandom(false);

        ImageUtils.saveFractalFlame(
            fractalImage,
            Path.of("output/fractal.png"),
            ImageFormat.PNG
        );

        System.out.println("Фрактал успешно сохранен в output/fractal.png");
    }
}
