package backend.academy.model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

@NoArgsConstructor
@EqualsAndHashCode
public class Color {

    private int r;
    private int g;
    private int b;

    private int hitCount;

    private double normal = 1;

    private static final int COLOR_BOUND = 256;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = 1;
    }

    public static Color createRandomColor() {
        Random random = ThreadLocalRandom.current();
        return new Color(
            random.nextInt(COLOR_BOUND),
            random.nextInt(COLOR_BOUND),
            random.nextInt(COLOR_BOUND)
        );
    }

    public static Color getEmpty() {
        return new Color();
    }

    public void mix(Color color) {
        int newHitCount = hitCount + color.getHitCount();
        r = (r * hitCount + color.getR() * color.getHitCount()) / newHitCount;
        g = (g * hitCount + color.getG() * color.getHitCount()) / newHitCount;
        b = (b * hitCount + color.getB() * color.getHitCount()) / newHitCount;
        hitCount = newHitCount;
    }

    public double computeNormal() {
        normal = log10(hitCount);
        return normal;
    }

    public void processGammaCorrection(double max, double gamma) {
        normal /= max;
        double c = pow(normal, 1. / gamma);
        r = (int) (r * c);
        g = (int) (g * c);
        b = (int) (b * c);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getHitCount() {
        return hitCount;
    }
}
