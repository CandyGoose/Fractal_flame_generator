package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class PowerTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double theta = theta(point);
        double coef = pow(sqrt(rSquare(point)), sin(theta));
        return new Point(
            coef * cos(theta),
            coef * sin(theta)
        );
    }
}
