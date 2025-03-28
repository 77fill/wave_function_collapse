package dev.pschmalz.wave_function_collapse.usecase;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class Util {
    Stream<File> getSelectedFiles(JFileChooser chooser) {
        return Arrays.stream(chooser.getSelectedFiles());
    }
}
