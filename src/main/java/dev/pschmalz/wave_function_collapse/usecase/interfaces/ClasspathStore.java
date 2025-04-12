package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.io.Closeable;

public interface ClasspathStore extends Closeable {
    Try<Stream<Image>> getExampleImages();
}
