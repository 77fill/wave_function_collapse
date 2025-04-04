package dev.pschmalz.wave_function_collapse.usecase.data;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public record Image(String name, InputStream content) implements Closeable {
    @Override
    public void close() throws IOException {
        content.close();
    }
}
