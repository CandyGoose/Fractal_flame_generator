package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.sin;

public class SinusoidalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        return new Point(
            sin(point.x()),
            sin(point.y())
        );
    }
}
