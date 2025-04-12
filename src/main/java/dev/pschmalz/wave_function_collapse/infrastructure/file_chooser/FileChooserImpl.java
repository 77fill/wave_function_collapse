package dev.pschmalz.wave_function_collapse.infrastructure.file_chooser;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import io.vavr.Function1;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
public class FileChooserImpl implements FileChooser {
    private final JFileChooser jFileChooser;
    private final Lock jFileChooserLock = new ReentrantLock();

    @Override
    public Stream<File> chooseImagePaths(File startDirectory) {
        return Stream.continually(
            () -> {
                jFileChooserLock.lock();
                // BLOCKS UNTIL FREE

                jFileChooser.setCurrentDirectory(startDirectory);
                var userAnswer = jFileChooser.showOpenDialog(null);
                // BLOCKS UNTIL USER IS DONE

                var result = Option.when(
                        userAnswer == JFileChooser.APPROVE_OPTION,
                        jFileChooser::getSelectedFiles);

                jFileChooserLock.unlock();
                return result;
            }).take(1)
                .flatMap(Function1.identity())
                .flatMap(Stream::of);
    }
}
