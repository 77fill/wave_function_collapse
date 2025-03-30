package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class ChooseTileImages_CreateTiles {
    @Autowired
    private FileSystem_TempDirectory tempDir;
    @Autowired
    private TileSet tiles;
    private JFileChooser chooser;
    @Autowired
    private UtilUsecase util;
    @Autowired
    private View view;

    @Async("background")
    public void run() {

        tempDir.getPath()
                .map(Path::toFile)
                .ifPresent(chooser::setCurrentDirectory);

        var buttonOption = chooser.showOpenDialog(null);

        if(buttonOption == JFileChooser.APPROVE_OPTION)
            Stream.of(chooser)
                    .flatMap(util::getSelectedFiles)
                    .forEach(tiles::add);

        this.tilesLoaded();
    }

    @Async("display")
    public void tilesLoaded() {
        view.tilesLoaded();
    }

}
