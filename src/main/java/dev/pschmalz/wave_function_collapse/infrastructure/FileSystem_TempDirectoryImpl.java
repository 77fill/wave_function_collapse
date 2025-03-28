package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.usecase.data.CustomResource;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static java.nio.file.Files.notExists;

@Component
public class FileSystem_TempDirectoryImpl implements FileSystem_TempDirectory {
    private Optional<Path> tempDir;

    @Override
    public Optional<Path> getPath() {
        return tempDir;
    }

    @Override
    public boolean unavailable() {
        return tempDir.isEmpty()
                || notExists(tempDir.get());
    }

    @Override
    public boolean available() {
        return !unavailable();
    }

    @Override
    public void create() throws IOException {
        Files.createTempDirectory(null);
    }

    @Override
    public void insert(CustomResource resource) throws IOException {
        if(unavailable()) throw new IOException("tempDir can't be accessed");

        var target = tempDir.get().resolve(resource.relativePath());

        Files.copy(
                resource.content(),
                target);
    }

    private void delete() throws IOException {
        if(tempDir.isEmpty())
            return;

        try ( var paths = Files.walk(tempDir.get()) ) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

    }
}
