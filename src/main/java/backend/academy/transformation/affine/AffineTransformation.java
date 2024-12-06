package backend.academy.transformation.affine;

import backend.academy.model.AffineTransformationCoefficients;
import backend.academy.model.Color;
import backend.academy.model.Point;
import backend.academy.transformation.Transformation;

public record AffineTransformation(AffineTransformationCoefficients coefficients, Color color)
    implements Transformation {

    /**
     * Returns: new Point(newX, newY)
     *  / a  b \    / x \    / c \    / newX \
     * |       | * |    | + |    | = |       |
     * \ d  e /    \ y /    \ f /    \ newY /
     */
    @Override
    public Point apply(Point point) {
        return new Point(
            coefficients.a() * point.x() + coefficients.b() * point.y() + coefficients.c(),
            coefficients.d() * point.x() + coefficients.e() * point.y() + coefficients.f()
        );
    }
}
