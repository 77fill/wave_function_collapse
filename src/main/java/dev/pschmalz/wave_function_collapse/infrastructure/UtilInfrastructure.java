package dev.pschmalz.wave_function_collapse.infrastructure;

import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.stream.Streams;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Component
public class UtilInfrastructure {
    public String getName(ClassPath.ResourceInfo resourceInfo) {
        return resourceInfo.getResourceName();
    }

    public String getSuffix(String fileName) {
        return Arrays.stream(fileName.split("\\."))
                .toList()
                .getLast();
    }

    public <T> Pair<T,T> toPair(T obj) {
        return ImmutablePair.of(obj, obj);
    }

    public InputStream getContent(ClassPath.ResourceInfo resourceInfo) throws IOException {
        return resourceInfo.asByteSource().openBufferedStream();
    }

    public <T> ExtendedStream<T> extendedStream(Collection<T> items) {
        return new ExtendedStream<>(Streams.failableStream(items));
    }

    public <T> ExtendedStream<T> extendedStream(T onlyOne) {
        return extendedStream(List.of(onlyOne));
    }

    public static class ExtendedStream<T> {
        private Streams.FailableStream<T> stream;

        public ExtendedStream(Streams.FailableStream<T> stream) {
            this.stream = stream;
        }

        public <R> ExtendedStream<R> mapWithIndex(com.google.common.collect.Streams.FunctionWithIndex<T,R> mapper) {
            return new ExtendedStream<>(
                    Streams.failableStream(
                            com.google.common.collect.Streams.mapWithIndex(stream.stream(),mapper)
                    )
            );
        }

        public ExtendedStream<T> peek(Consumer<T> consumer) {
            return new ExtendedStream<>(Streams.failableStream(stream.stream().peek(consumer)));
        }

        public <U,V, R,S> ExtendedStream<Pair<R,S>> mapPair(FailableFunction<U, R, ?> mapperLeft, FailableFunction<V, S, ?> mapperRight) {
            var specificStream = (Streams.FailableStream<Pair<U,V>>) stream;

            return new ExtendedStream<>(
                    specificStream.map(pair ->
                            ImmutablePair.of(
                                    mapperLeft.apply(pair.getLeft()),
                                    mapperRight.apply(pair.getRight())
                            )) );
        }

        public Streams.FailableStream<T> toFailableStream() {
            return stream;
        }

        public <R> ExtendedStream<R> flatMap(Function<T,Collection<R>> oneToCollection) {
            Function<T,Stream<R>> oneToStream =
                    one -> oneToCollection.apply(one).stream();

            return new ExtendedStream<>( Streams.failableStream( stream.stream().flatMap(oneToStream) ) );
        }

        public ExtendedStream<Pair<T,T>> duplicateIntoPair() {
            return map(item -> ImmutablePair.of(item,item));
        }

        // Delegating to stream

        public boolean allMatch(FailablePredicate<T, ?> predicate) {
            return stream.allMatch(predicate);
        }

        public Stream<T> stream() {
            return stream.stream();
        }

        public <R> ExtendedStream<R> map(FailableFunction<T, R, ?> mapper) {
            return new ExtendedStream<>( stream.map(mapper) );
        }

        public <A, R> R collect(Collector<? super T, A, R> collector) {
            return stream.collect(collector);
        }

        public void forEach(FailableConsumer<T, ?> action) {
            stream.forEach(action);
        }

        public T reduce(T identity, BinaryOperator<T> accumulator) {
            return stream.reduce(identity, accumulator);
        }

        public boolean anyMatch(FailablePredicate<T, ?> predicate) {
            return stream.anyMatch(predicate);
        }

        public ExtendedStream<T> filter(FailablePredicate<T, ?> predicate) {
            return new ExtendedStream<>( stream.filter(predicate) );
        }

        public <A, R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
            return stream.collect(supplier, accumulator, combiner);
        }
    }

}

