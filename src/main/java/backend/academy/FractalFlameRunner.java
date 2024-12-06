package backend.academy;

import backend.academy.format.ImageFormat;
import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;
import backend.academy.renderer.DefaultRenderer;
import backend.academy.renderer.ParallelRenderer;
import backend.academy.renderer.Renderer;
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
import backend.academy.utils.ImageUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalFlameRunner {

    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;
    private static final int DEFAULT_ITERATIONS = 1_000_000;
    private static final int DEFAULT_SAMPLES = 10;
    private static final int DEFAULT_SYMMETRY = 1;
    private static final boolean DEFAULT_USE_PARALLEL = true;
    private static final ImageFormat DEFAULT_FORMAT = ImageFormat.PNG;
    private static final Path DEFAULT_OUTPUT_DIR = Paths.get("output");
    private static final double GAMMA_CORRECTION = 2.2;
    private static final int MIN_WIDTH = 100;
    private static final int MAX_WIDTH = 10000;
    private static final int MIN_HEIGHT = 100;
    private static final int MAX_HEIGHT = 10000;
    private static final int MIN_ITERATIONS = 100;
    private static final int MAX_ITERATIONS = 10_000_000;
    private static final int MIN_SAMPLES = 1;
    private static final int MAX_SAMPLES = 100;
    private static final int MIN_SYMMETRY = 1;
    private static final int MAX_SYMMETRY = 20;

    private static final double NANOSECONDS_IN_SECOND = 1_000_000_000.0;

    private static final String DEFAULT_PROMPT = " (по умолчанию %s): ";
    private static final String RANGE_WARNING =
        "Значение должно быть в диапазоне [%d, %d]. Используется значение по умолчанию: %d\n";
    private static final String INVALID_INPUT_WARNING =
        "Некорректный ввод. Используется значение по умолчанию: %d\n";
    private static final String INVALID_TRANSFORMATION_WARNING =
        "Некорректный ввод или ошибка при создании трансформации: %s\n";

    public void run(PrintStream out) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int width = getUserInput(scanner, out,
            "Введите ширину изображения" + String.format(DEFAULT_PROMPT, DEFAULT_WIDTH),
            DEFAULT_WIDTH, MIN_WIDTH, MAX_WIDTH);
        int height = getUserInput(scanner, out,
            "Введите высоту изображения" + String.format(DEFAULT_PROMPT, DEFAULT_HEIGHT),
            DEFAULT_HEIGHT, MIN_HEIGHT, MAX_HEIGHT);
        int iterations = getUserInput(scanner, out,
            "Введите количество итераций" + String.format(DEFAULT_PROMPT, DEFAULT_ITERATIONS),
            DEFAULT_ITERATIONS, MIN_ITERATIONS, MAX_ITERATIONS);
        int samples = getUserInput(scanner, out,
            "Введите количество сэмплов" + String.format(DEFAULT_PROMPT, DEFAULT_SAMPLES),
            DEFAULT_SAMPLES, MIN_SAMPLES, MAX_SAMPLES);

        out.println("Доступные трансформации:");
        List<Class<? extends Transformation>> transformations = List.of(
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

        List<Transformation> selectedTransformations = getTransformationsFromUser(scanner, out, transformations);

        int symmetry = getUserInput(scanner, out, "Введите симметрию"
                + String.format(DEFAULT_PROMPT, DEFAULT_SYMMETRY),
            DEFAULT_SYMMETRY, MIN_SYMMETRY, MAX_SYMMETRY);
        boolean useParallel = getUserBooleanInput(scanner, out,
            "Использовать многопоточность?"
                + String.format(DEFAULT_PROMPT, DEFAULT_USE_PARALLEL), DEFAULT_USE_PARALLEL);
        ImageFormat format = getUserFormatInput(scanner, out,
            "Введите формат изображения (png, jpg, bmp)"
                + String.format(DEFAULT_PROMPT, DEFAULT_FORMAT.name().toLowerCase()),
            DEFAULT_FORMAT);

        Renderer renderer;
        long startTime = System.nanoTime();
        if (useParallel) {
            renderer = new ParallelRenderer(width, height, selectedTransformations);
            out.println("Используется многопоточный рендеринг.");
            out.printf("Доступно процессоров: %d%n", Runtime.getRuntime().availableProcessors());
        } else {
            renderer = new DefaultRenderer(width, height, selectedTransformations);
            out.println("Используется однопоточный рендеринг.");
        }

        FractalImage fractalImage = renderer.render(
            Rectangle.getDefaultRectangle(),
            samples,
            iterations,
            symmetry,
            GAMMA_CORRECTION
        );

        long duration = System.nanoTime() - startTime;
        out.printf("Рендер занял: %.2f сек%n", duration / NANOSECONDS_IN_SECOND);

        Path outputDir = DEFAULT_OUTPUT_DIR;
        if (Files.notExists(outputDir)) {
            Files.createDirectories(outputDir);
        }
        Path outputPath = outputDir.resolve("fractal_" + System.currentTimeMillis()
            + "." + format.name().toLowerCase());
        ImageUtils.saveFractalFlame(fractalImage, outputPath, format);

        out.printf("Фрактал успешно сохранен в: %s%n", outputPath.toAbsolutePath());
    }

    private static List<Transformation> getTransformationsFromUser(Scanner scanner, PrintStream out,
        List<Class<? extends Transformation>> transformations) {
        List<Transformation> selectedTransformations = new ArrayList<>();
        for (int i = 0; i < transformations.size(); i++) {
            out.printf("%d. %s%n", i + 1, transformations.get(i).getSimpleName());
        }
        out.println("Выберите трансформации, вводя номера через запятую (например, 1,3,5):");
        String input = scanner.nextLine();
        String[] choices = input.split(",");
        for (String choice : choices) {
            try {
                int index = Integer.parseInt(choice.trim()) - 1;
                if (index >= 0 && index < transformations.size()) {
                    selectedTransformations.add(transformations.get(index).getDeclaredConstructor().newInstance());
                }
            } catch (Exception e) {
                out.printf(INVALID_TRANSFORMATION_WARNING, choice.trim());
            }
        }
        if (selectedTransformations.isEmpty()) {
            out.println("Не выбрано ни одной трансформации. Используются все доступные.");
            for (Class<? extends Transformation> transformation : transformations) {
                try {
                    selectedTransformations.add(transformation.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    out.printf("Ошибка при создании трансформации: %s%n", transformation.getSimpleName());
                }
            }
        }
        return selectedTransformations;
    }

    private static int getUserInput(Scanner scanner, PrintStream out, String prompt,
        int defaultValue, int minValue, int maxValue) {
        out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            int value = input.isEmpty() ? defaultValue : Integer.parseInt(input);
            if (value < minValue || value > maxValue) {
                out.printf(RANGE_WARNING, minValue, maxValue, defaultValue);
                return defaultValue;
            }
            return value;
        } catch (NumberFormatException e) {
            out.printf(INVALID_INPUT_WARNING, defaultValue);
            return defaultValue;
        }
    }

    private static boolean getUserBooleanInput(Scanner scanner, PrintStream out, String prompt, boolean defaultValue) {
        out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("да");
    }

    private static ImageFormat getUserFormatInput(Scanner scanner, PrintStream out, String prompt,
        ImageFormat defaultValue) {
        out.print(prompt);
        String input = scanner.nextLine().trim().toUpperCase();
        try {
            return input.isEmpty() ? defaultValue : ImageFormat.valueOf(input);
        } catch (IllegalArgumentException e) {
            out.printf("Некорректный формат. Используется значение по умолчанию: %s%n",
                defaultValue.name().toLowerCase());
            return defaultValue;
        }
    }
}
