package dev.pschmalz.wave_function_collapse.infrastructure;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.usecase.data.CustomResource;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.stream.Streams;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

public class ResourceStoreImpl implements ResourceStore {
    private ClassPath classPath;
    private ImmutableSet<String> allowedNameSuffixes;
    private Util util;

    private ResourceStoreImpl() throws IOException {
        classPath = ClassPath.from(ClassLoader.getPlatformClassLoader());
        util = new Util();
    }

    public static ResourceStoreImpl withAllowedFileNameSuffixes(Collection<String> suffixes) throws IOException {
        var resources = new ResourceStoreImpl();

        resources.allowedNameSuffixes = ImmutableSet.copyOf(suffixes);

        return resources;
    }

    @Override
    public Streams.FailableStream<CustomResource> all() {
        return util.extendedStream(
                    classPath
                )

                .flatMap(ClassPath::getResources)
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
}
