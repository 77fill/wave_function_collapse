package dev.pschmalz.wave_function_collapse.infrastructure;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.CustomResource;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.stream.Streams;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ResourceStoreImpl implements ResourceStore, Closeable {
    @Autowired
    private ClassPath classPath;
    @Value("${infrastructure.contained-resources.choose-file-name-suffixes}")
    private List<String> allowedNameSuffixes;
    @Autowired
    private UtilInfrastructure util;
    private Collection<Closeable> toBeClosed = new ArrayList<>();

    @Override
    public Streams.FailableStream<CustomResource> all() {
        System.out.println("suffixes:"+allowedNameSuffixes.toString());
        return util.extendedStream(
                    classPath
                )

                .flatMap(ClassPath::getResources)
                .filter(isInside("dev.pschmalz.wave_function_collapse"))
                .filter(nameSuffixIn(allowedNameSuffixes))
                .duplicateIntoPair()
                .mapPair(util::getName, util::getContent)
                .map(CustomResource::new)

                .toFailableStream();
    }

    private <T,U,V,W> FailableFunction<Pair<T,U>,Pair<V,W>,?> pair(FailableFunction<T,V,?> onLeft, FailableFunction<U,W,?> onRight) {
        return input -> ImmutablePair.of(
                onLeft.apply(input.getLeft()),
                onRight.apply(input.getRight()));
    }

    private FailablePredicate<ClassPath.ResourceInfo,?> nameSuffixIn(Collection<String> allowedNameSuffixes) {
        return resourceInfo -> Stream.of(resourceInfo)

                .map(util::getName)
                .map(util::getSuffix)
                .map(allowedNameSuffixes::contains)

                .findFirst()
                .get();
    }

    private FailablePredicate<ClassPath.ResourceInfo,?> isInside(String packageName) {
        var packageNameSegments = Arrays.asList(packageName.split("\\."));

        return resourceInfo -> {
            var segments = resourceNameSegments(resourceInfo);
            return segments.subList(
                    0, Math.min(segments.size(),packageNameSegments.size())
            ).equals(packageNameSegments);
        };
    }

    private List<String> resourceNameSegments(ClassPath.ResourceInfo resourceInfo) {
        return Arrays.asList(
                resourceInfo.getResourceName()
                        .split("[/\\\\]")
        );
    }

    @Override
    @PreDestroy
    public void close() throws IOException {
        Streams.failableStream(toBeClosed)
                .forEach(Closeable::close);
    }
}
