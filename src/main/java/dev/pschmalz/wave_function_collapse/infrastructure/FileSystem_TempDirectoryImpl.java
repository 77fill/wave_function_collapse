package dev.pschmalz.wave_function_collapse.infrastructure;

import jakarta.annotation.PostConstruct;
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
    private Optional<Path> tempDir = Optional.empty();

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
    @PostConstruct
    public void create() throws IOException {
        tempDir = Optional.of(Files.createTempDirectory(null));
    }

    @Override
    public void insert(CustomResource resource) throws IOException {
        if(unavailable()) throw new IOException("tempDir can't be accessed");

        var target = tempDir.get().resolve(resource.getFileName());

        Files.copy(
                resource.content(),
                target);

        System.out.printf("resource copied. target: %s%n", target);
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
