package dev.pschmalz.wave_function_collapse.infrastructure.classpath_store;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import io.vavr.collection.List;
import io.vavr.control.Try;
import lombok.Builder;
import lombok.Value;

import java.io.InputStream;
import java.nio.file.Path;


import static io.vavr.API.Try;
import static io.vavr.control.Option.some;

@Value
public class DecoratedResource {
    List<String> allowedImageSuffixes;
    Path projectBasePath;
    ClassPath.ResourceInfo resourceInfo;

    /*public static Builder with(List<String> allowedImageSuffixes, Path projectBasePath) {
        return new Builder(allowedImageSuffixes, projectBasePath);
    }*/

    public boolean isExampleImage() {
        return isInsideThisProject()
                && hasAllowedImageSuffix();
    }

    public Try<Image> toImage() {
        return getContent().map(content -> new Image(getName(), content));
    }

    private String getName() {
        return getPath().getFileName().toString();
    }

    private Try<InputStream> getContent() {
        return Try(() -> resourceInfo.asByteSource().openBufferedStream());
    }

    private Path getPath() {
        return Path.of(resourceInfo.getResourceName());
    }

    private boolean isInsideThisProject() {
        return getPath()
                .startsWith(this.projectBasePath);
    }

    private boolean hasAllowedImageSuffix() {
        return some(resourceInfo)
                .map(ClassPath.ResourceInfo::getResourceName)
                .map(Path::of)
                .map(this::getSuffix)
                .filter(allowedImageSuffixes::contains)
                .isDefined();
    }

    private String getSuffix(Path path) {
        var segments = path.getFileName().toString().split("\\.");
        return segments[segments.length - 1];
    }

    /*public static class Builder {
        private final List<String> allowedImageSuffixes;
        private final Path projectBasePath;

        private Builder(List<String> allowedImageSuffixes, Path projectBasePath) {
            this.allowedImageSuffixes = allowedImageSuffixes;
            this.projectBasePath = projectBasePath;
        }

        public DecoratedResource decorate(ClassPath.ResourceInfo resourceInfo) {
            return new DecoratedResource(resourceInfo, allowedImageSuffixes, projectBasePath);
        }
    }*/
}
