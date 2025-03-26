package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.CustomResource;
import org.apache.commons.lang3.stream.Streams;

public interface ResourceStore {
    public Streams.FailableStream<CustomResource> all();
}
