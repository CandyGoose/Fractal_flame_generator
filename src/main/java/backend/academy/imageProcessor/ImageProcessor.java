package backend.academy.imageProcessor;

import backend.academy.model.FractalImage;

@FunctionalInterface
public interface ImageProcessor {

    void process(FractalImage image);
}
