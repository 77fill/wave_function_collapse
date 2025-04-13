package dev.pschmalz.wave_function_collapse.infrastructure.file_chooser;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import io.vavr.Function1;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileChooserImpl implements FileChooser {
    JFileChooser jFileChooser;
    Lock jFileChooserLock = new ReentrantLock();

    @Override
    public Stream<File> chooseImagePaths(File startDirectory) {

        jFileChooserLock.lock();
        // BLOCKS UNTIL FREE

        jFileChooser.setCurrentDirectory(startDirectory);
        var userAnswer = jFileChooser.showOpenDialog(null);
        // BLOCKS UNTIL USER IS DONE

        var result = Option.when(
                userAnswer == JFileChooser.APPROVE_OPTION,
                jFileChooser::getSelectedFiles);

        jFileChooserLock.unlock();

        return Stream.of(result.getOrElse(new File[0]));

    }
}
