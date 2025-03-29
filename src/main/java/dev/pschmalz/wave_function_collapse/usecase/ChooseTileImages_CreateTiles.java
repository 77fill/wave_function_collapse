package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@Component
public class ChooseTileImages_CreateTiles {
    private FileSystem_TempDirectory tempDir;
    private TileSet tiles;
    private JFileChooser chooser;
    private Util util;
    private View view;

    @Async("background")
    public void execute(View view) {
        this.view = view;

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

    public ChooseTileImages_CreateTiles(FileSystem_TempDirectory tempDir, TileSet tiles, Util util, Executor background, Executor display) {
        this.tempDir = tempDir;
        this.tiles = tiles;
        this.chooser = new JFileChooser();
        this.util = util;

        chooser.setMultiSelectionEnabled(true);
    }
}
