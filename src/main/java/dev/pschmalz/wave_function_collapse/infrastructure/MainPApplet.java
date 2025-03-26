package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.machinery.Constrainer;
import dev.pschmalz.wave_function_collapse.model.ConstrainedTile;
import dev.pschmalz.wave_function_collapse.model.Tile;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages_CreateTiles;
import dev.pschmalz.wave_function_collapse.usecase.LoadResources_IntoTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import processing.core.PApplet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainPApplet extends PApplet implements View {
    private Queue<Runnable> eventQueue;
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    private ChooseTileImages_CreateTiles chooseTileImages_createTiles;

    private List<ConstrainedTile> constrainedTiles = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    public MainPApplet(Queue<Runnable> eventQueue, LoadResources_IntoTempDirectory loadResources_intoTempDirectory, ChooseTileImages_CreateTiles chooseTileImages_createTiles) {
        this.eventQueue = eventQueue;
        this.loadResources_intoTempDirectory = loadResources_intoTempDirectory;
        this.chooseTileImages_createTiles = chooseTileImages_createTiles;
    }

    @Override
    public void settings() {
        size(1000,1000);
        noSmooth();
    }

    @Override
    public void setup() {
        loadResources_intoTempDirectory.execute(this);
    }

    @Override
    public void draw() {
        background(0);
        drawTiles(30, 30, constrainedTiles.stream().map(ct -> ct.getTile()).toList());

        eventQueue.forEach(Runnable::run);
    }

    private void loadTiles() {
        var chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "gif", "jpg", "tga", "png"));
        var answer = chooser.showOpenDialog(null);

        if(answer == JFileChooser.APPROVE_OPTION) {
            var files = chooser.getSelectedFiles();
            for(File file : files) {
                var img = loadImage(file.getAbsolutePath());
                if(img != null)
                    tiles.add(new Tile(img));
            }
        }

    }

    private void drawTiles(int tileWidth, int tileHeight, List<Tile> tiles) {
        int currentTileX = 1, currentTileY = 1;
        for(var currentTile : tiles) {
            if(width < currentTileX + tileWidth + 1) {
                currentTileX = 1;
                currentTileY += tileHeight + 1;
            }

            if(height < currentTileY + tileHeight + 1)
                break;

            image(currentTile.getImage(), currentTileX, currentTileY, tileWidth, tileHeight);

            currentTileX += tileWidth + 1;
        }
    }

    private void contrainTiles() {
        var constrainer = new Constrainer();
        constrainedTiles = constrainer.contrain(tiles);
    }

    @Override
    public void tempDirectoryLoaded() {

    }

    @Override
    public void tilesLoaded() {

    }
}
