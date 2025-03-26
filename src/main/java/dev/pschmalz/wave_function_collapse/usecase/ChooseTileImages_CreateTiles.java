package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public class ChooseTileImages_CreateTiles {
    private FileSystem_TempDirectory tempDir;
    private TileManager tiles;
    private JFileChooser chooser;
    private Util util;
    private View view;
    private Executor background, display;

    public void execute(View view) {
        this.view = view;

        background.execute(this::executeProper);

    }

    private void executeProper() {
        tempDir.getPath()
                .map(Path::toFile)
                .ifPresent(chooser::setCurrentDirectory);

        var buttonOption = chooser.showOpenDialog(null);

        if(buttonOption == JFileChooser.APPROVE_OPTION)
            Stream.of(chooser)
                    .flatMap(util::getSelectedFiles)
                    .forEach(tiles::add);

        display.execute(view::tilesLoaded);
    }

    public ChooseTileImages_CreateTiles(FileSystem_TempDirectory tempDir, TileManager tiles, Executor background, Executor display) {
        this.tempDir = tempDir;
        this.tiles = tiles;
        this.chooser = new JFileChooser();
        this.util = new Util();
        this.background = background;
        this.display = display;

        chooser.setMultiSelectionEnabled(true);
    }
}
