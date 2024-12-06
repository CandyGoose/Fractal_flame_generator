package backend.academy;

import backend.academy.format.ImageFormat;
import backend.academy.utils.ImageUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) throws IOException {

        Path outputDir = Paths.get("output");

        if (Files.notExists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        for (int i = 0; i < 1; i++) {
            ImageUtils.saveFractalFlame(
                FractalFlameGenerator.generateRandom(false),
                outputDir,
                ImageFormat.PNG
            );
        }
    }
}
