package backend.academy;

import java.io.IOException;
import java.io.PrintStream;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        PrintStream out = System.out;

        try {
            FractalFlameRunner.run(out);
        } catch (IOException e) {
            LOGGER.error("Ошибка ввода/вывода: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Непредвиденная ошибка: {}", e.getMessage(), e);
        }
    }
}
