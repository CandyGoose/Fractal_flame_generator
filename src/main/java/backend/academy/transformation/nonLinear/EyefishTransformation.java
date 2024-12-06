package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.sqrt;

public class EyefishTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double coef = 2 / (sqrt(rSquare(point)) + 1);
        return new Point(
            coef * point.x(),
            coef * point.y()
        );
    }
}
