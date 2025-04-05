package dev.pschmalz.wave_function_collapse.infrastructure.classpath_store;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.stream.Streams;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClasspathStoreImpl implements ClasspathStore {
    private final ClassPath classPath;
    private final List<String> allowedNameSuffixes;
    private final Set<Closeable> closeables = new HashSet<>();
    private final Path projectBasePath;

    @Override
    public Flux<Image> getExampleImages() {
        return Mono.just(classPath)
                    .flatMapIterable(ClassPath::getResources)
                    .map(this::decorate)
                    .filter(DecoratedResource::isExampleImage)
                    .map(DecoratedResource::toImage)
                    .doOnNext(this::closeImageLater)
                    .doOnTerminate(this::close);
    }

    private DecoratedResource decorate(ClassPath.ResourceInfo resourceInfo) {
        return DecoratedResource
                .with(allowedNameSuffixes, projectBasePath)
                .decorate(resourceInfo);
    }

    private void closeImageLater(Image image) {
        closeables.add(image);
    }

    @PreDestroy
    private void close() {
        Streams.failableStream(closeables).forEach(Closeable::close);
        closeables.clear();
    }

    public ClasspathStoreImpl(List<String> allowedNameSuffixes, ClassPath classPath, Path thisProject) {
        this.allowedNameSuffixes = allowedNameSuffixes;
        this.classPath = classPath;
        this.projectBasePath = thisProject;
    }

}
