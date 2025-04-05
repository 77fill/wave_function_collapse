package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import reactor.core.publisher.Flux;

public interface ClasspathStore {
    Flux<Image> getExampleImages();
}
