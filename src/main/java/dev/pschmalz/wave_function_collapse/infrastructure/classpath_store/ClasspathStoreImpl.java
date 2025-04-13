package dev.pschmalz.wave_function_collapse.infrastructure.classpath_store;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import io.vavr.Function3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.stream.Streams;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static io.vavr.API.Function;
import static io.vavr.API.Stream;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClasspathStoreImpl implements ClasspathStore {
    ClassPath classPath;
    List<String> allowedNameSuffixes;
    Set<Closeable> closeables = new HashSet<>();
    Path projectBasePath;

    @Override
    public Try<Stream<Image>> getExampleImages() {
        return Stream(classPath)
                    .flatMap(ClassPath::getResources)
                    .map(newDecoratedResource.apply(allowedNameSuffixes, projectBasePath))
                    .filter(DecoratedResource::isExampleImage)
                    .map(DecoratedResource::toImage)
                    .transform(Try::sequence)
                    .map(Seq::toStream)
                    .andThen(images -> images.forEach(this::closeImageLater));
    }

    private final Function3<List<String>,Path, ClassPath.ResourceInfo,DecoratedResource>
        newDecoratedResource = Function(DecoratedResource::new);

    private void closeImageLater(Image image) {
        closeables.add(image);
    }

    @PreDestroy
    public void close() {
        Streams.failableStream(closeables).forEach(Closeable::close);
        closeables.clear();
    }

}
