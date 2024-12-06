package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.sin;

public class ExponentialTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double exp = exp(point.x() - 1);
        return new Point(
            exp * cos(PI * point.y()),
            exp * sin(PI * point.y())
        );
    }
}
