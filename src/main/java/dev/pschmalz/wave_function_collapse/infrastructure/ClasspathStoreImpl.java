package dev.pschmalz.wave_function_collapse.infrastructure;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.stream.Streams;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ClasspathStoreImpl implements ClasspathStore {
    private final ClassPath classPath;
    private List<String> allowedNameSuffixes;
    private final Set<Closeable> closeables = new HashSet<>();


    @Override
    public Flux<Image> getExampleImages() {
        return Mono.just(classPath)
                    .flatMapIterable(ClassPath::getResources)
                    .filter(resourceIsExampleImage)
                    .map(this::toImage)
                    .doOnTerminate(this::close);
    }

    @PreDestroy
    private void close() {
        Streams.failableStream(closeables).forEach(Closeable::close);
        closeables.clear();
    }

    private final Path projectBasePath = Path.of("dev", "pschmalz", "wave_function_collapse");

    private final Predicate<ClassPath.ResourceInfo>
            isInsideThisArtifact = res -> pathOf(res).startsWith(projectBasePath),
            hasAllowedImageSuffix = res -> Mono.just(res).map(this::pathOf).map(this::getSuffix).filter(this::suffixAllowed).hasElement().blockOptional().get(),
            resourceIsExampleImage = isInsideThisArtifact.and(hasAllowedImageSuffix);

    private Path pathOf(ClassPath.ResourceInfo resourceInfo) {
        return Path.of(resourceInfo.getResourceName());
    }

    private String getSuffix(Path path) {
        var segments = path.getFileName().toString().split("\\.");
        return segments[segments.length - 1];
    }

    private boolean suffixAllowed(String suffix) {
        return allowedNameSuffixes.contains(suffix);
    }

    private Image toImage(ClassPath.ResourceInfo resourceInfo) {
        return new Image(nameOf(resourceInfo), contentOf(resourceInfo));
    }

    private String nameOf(ClassPath.ResourceInfo resourceInfo) {
       return pathOf(resourceInfo).getFileName().toString();
    }

    private InputStream contentOf(ClassPath.ResourceInfo resourceInfo) {
        try {
            var result = resourceInfo.asByteSource().openBufferedStream();

            closeables.add(result);

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ClasspathStoreImpl(List<String> allowedNameSuffixes, ClassPath classPath) {
        this.allowedNameSuffixes = allowedNameSuffixes;
        this.classPath = classPath;
    }

}
