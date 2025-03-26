package dev.pschmalz.wave_function_collapse.usecase.data;

import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;

public record CustomResource(String relativePath, InputStream content) {
    public CustomResource(Pair<String,InputStream> config) {
        this(
                config.getLeft(),
                config.getRight()
        );
    }
}
