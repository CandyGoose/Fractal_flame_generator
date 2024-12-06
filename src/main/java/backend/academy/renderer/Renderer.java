package backend.academy.renderer;

import backend.academy.model.FractalImage;
import backend.academy.model.Rectangle;

public interface Renderer {

    FractalImage render(
        Rectangle world,
        int samples,
        int iterPerSample,
        int symmetry,
        double gammaCorrection
    );
}
