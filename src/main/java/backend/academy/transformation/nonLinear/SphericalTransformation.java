package backend.academy.transformation.nonLinear;

import backend.academy.model.Point;
import backend.academy.transformation.Transformation;

public class SphericalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double rSquare = rSquare(point);
        return new Point(
            point.x() / rSquare,
            point.y() / rSquare
        );
    }
}
