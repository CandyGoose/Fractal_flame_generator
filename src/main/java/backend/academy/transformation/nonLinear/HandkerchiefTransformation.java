package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class HandkerchiefTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = sqrt(rSquare(point));
        return new Point(
            r * sin(theta(point) + r),
            r * cos(theta(point) - r)
        );
    }
}
