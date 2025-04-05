package dev.pschmalz.wave_function_collapse.infrastructure.classpath_store;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public class DecoratedResource {
    private final ClassPath.ResourceInfo resourceInfo;
    private final List<String> allowedImageSuffixes;
    private final Path projectBasePath;

    private DecoratedResource(ClassPath.ResourceInfo resourceInfo, List<String> allowedImageSuffixes, Path projectBasePath) {
        this.resourceInfo = resourceInfo;
        this.allowedImageSuffixes = allowedImageSuffixes;
        this.projectBasePath = projectBasePath;
    }

    public static Builder with(List<String> allowedImageSuffixes, Path projectBasePath) {
        return new Builder(allowedImageSuffixes, projectBasePath);
    }

    public boolean isExampleImage() {
        return isInsideThisProject()
                && hasAllowedImageSuffix();
    }

    public Image toImage() {
        return new Image(getName(), getContent());
    }

    private String getName() {
        return getPath().getFileName().toString();
    }

    private InputStream getContent() {
        try {
            var result = resourceInfo.asByteSource().openBufferedStream();


            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath() {
        return Path.of(resourceInfo.getResourceName());
    }

    private boolean isInsideThisProject() {
        return getPath()
                .startsWith(this.projectBasePath);
    }

    private boolean hasAllowedImageSuffix() {
        return Mono.just(resourceInfo)
                .map(ClassPath.ResourceInfo::getResourceName)
                .map(Path::of)
                .map(this::getSuffix)
                .filter(allowedImageSuffixes::contains)
                .hasElement()
                .blockOptional()
                .get();
    }

    private String getSuffix(Path path) {
        var segments = path.getFileName().toString().split("\\.");
        return segments[segments.length - 1];
    }

    public static class Builder {
        private final List<String> allowedImageSuffixes;
        private final Path projectBasePath;

        private Builder(List<String> allowedImageSuffixes, Path projectBasePath) {
            this.allowedImageSuffixes = allowedImageSuffixes;
            this.projectBasePath = projectBasePath;
        }

        public DecoratedResource decorate(ClassPath.ResourceInfo resourceInfo) {
            return new DecoratedResource(resourceInfo, allowedImageSuffixes, projectBasePath);
        }
    }
}
