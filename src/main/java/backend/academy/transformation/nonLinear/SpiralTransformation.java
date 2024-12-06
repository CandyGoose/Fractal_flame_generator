package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class SpiralTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = sqrt(rSquare(point));
        double theta = theta(point);
        return new Point(
            1 / r * (cos(theta) + sin(r)),
            1 / r * (sin(theta) - cos(r))
        );
    }
}
