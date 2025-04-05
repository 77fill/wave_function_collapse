package dev.pschmalz.wave_function_collapse.infrastructure.file_chooser;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import reactor.core.publisher.Flux;

import javax.swing.*;
import java.io.File;
import java.util.stream.Stream;

public class FileChooserImpl implements FileChooser {
    private final JFileChooser jFileChooser;

    @Override
    public Flux<File> chooseImagePaths(File startDirectory) {
        jFileChooser.setCurrentDirectory(startDirectory);

        return Flux.generate(sink -> {
            var userAnswer = jFileChooser.showOpenDialog(null);
            /*
             * BLOCK TILL USER IS DONE<br>
             * (But Flux is already returned)
             */
            if(userAnswer == JFileChooser.APPROVE_OPTION) {
                Stream.of(jFileChooser.getSelectedFiles())
                        .forEach(sink::next);
            }

            sink.complete();
        });
    }

    public FileChooserImpl(JFileChooser jFileChooser) {
        this.jFileChooser = jFileChooser;
    }
}
