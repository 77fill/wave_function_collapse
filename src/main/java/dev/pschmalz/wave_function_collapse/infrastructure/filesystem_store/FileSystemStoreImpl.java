package dev.pschmalz.wave_function_collapse.infrastructure.filesystem_store;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkState;
import static io.vavr.API.Tuple;

/**
 * FYI: This is possible: (pseudocode)<br>
 * <pre>
 *     Setting:
 *          Path a, b
 *          a.toString() == b.toString()
 *     Completely different outcomes!!:
 *          1. Files.createFile(a)
 *          2. Files.createFile(b)
 * </pre>
 * Reason: Paths reference a specific FileSystem!<br>
 * @see FileSystemStoreImpl#validate()
 * @see Path#getFileSystem()
 *
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileSystemStoreImpl implements FileSystemStore {
    /**
     * For mocking purposes (e.g. virtual filesystem in memory)
     */
    FileSystem fileSystem;
    Path tempDir;

    @Override
    public File getTempDirectoryPath() {
        return tempDir.toFile();
    }

    @Override
    public Image getImage(File imagePath) {
        return Tuple(imagePath)
                .map(this::fileToPath)
                .map(this::newInputStream)
                .map(this::toBufferedImage)
                .map(Image::new)
                ._1;
    }

    @Override
    public void addImageToTempDirectory(dev.pschmalz.wave_function_collapse.usecase.data.Image image) {
        try {
            System.out.println("addImageToTempDirectory name: "+image.name());
            Files.copy(image.content(), tempDir.resolve(image.name()));
            System.out.printf("file: %s -> Done%n", image.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path fileToPath(File file) {
        return fileSystem.getPath(file.getAbsolutePath());
    }

    //TODO use vavr/Try
    private BufferedImage toBufferedImage(InputStream inputStream) {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream newInputStream(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void validate() {
        checkState(tempDir.getFileSystem().equals(fileSystem), "Path 'tempDir' does not refer to the FileSystem 'fileSystem'");
        checkState(Files.isDirectory(tempDir), "Path 'tempDir' is not a directory");
        checkState(Files.isReadable(tempDir), "directory tempDir is not readable");
        checkState(Files.isWritable(tempDir), "directory tempDir is not writable");
    }

}
