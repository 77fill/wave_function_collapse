package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.CustomResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface FileSystem_TempDirectory {
    public boolean unavailable();
    public boolean available();
    public void create() throws IOException;
    public void insert(CustomResource resource) throws IOException;
    public Optional<Path> getPath();
}
