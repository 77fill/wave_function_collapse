package dev.pschmalz.wave_function_collapse;

import com.google.common.collect.ImmutableSet;
import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.infrastructure.FileSystem_TempDirectoryImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.MainPApplet;
import dev.pschmalz.wave_function_collapse.infrastructure.ResourceStoreImpl;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages_CreateTiles;
import dev.pschmalz.wave_function_collapse.usecase.LoadResources_IntoTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.ShowTileImages;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import processing.core.PApplet;

import java.io.IOException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    private static final Queue<Runnable>
            eventQueue =
                new ConcurrentLinkedQueue<>();

    private static final Set<String>
            ALLOWED_NAME_SUFFIXES =
                ImmutableSet.of("png");

    private static final int
            BACKGROUND_THREADS = 3;

    public static void main(String[] args) throws Exception {
        createDomain();
        createInfrastructure();
        createUsecases();

        PApplet.runSketch(
                new String[] {"", ""},

                new MainPApplet(
                        eventQueue,
                        loadResources_intoTempDirectory,
                        chooseTileImages_createTiles,
                        showTileImages
                ));
    }

    private static void createUsecases() {
        loadResources_intoTempDirectory =
                new LoadResources_IntoTempDirectory(
                        resources,
                        fileSystem_tempDir,
                        background,
                        display
                );

        chooseTileImages_createTiles =
                new ChooseTileImages_CreateTiles(
                        fileSystem_tempDir,
                        tiles,
                        background,
                        display
                );

        showTileImages =
                new ShowTileImages(
                        tiles,
                        display
                );
    }

    private static void createInfrastructure() throws IOException {
        resources = ResourceStoreImpl.withAllowedFileNameSuffixes(ALLOWED_NAME_SUFFIXES);
        fileSystem_tempDir = new FileSystem_TempDirectoryImpl();
        background = Executors.newFixedThreadPool(BACKGROUND_THREADS);
        display = eventQueue::add;
    }

    private static void createDomain() {
        tiles = new TileManager();
    }

    private static TileManager tiles;
    private static ResourceStore resources;
    private static FileSystem_TempDirectory fileSystem_tempDir;
    private static Executor background, display;
    private static LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    private static ChooseTileImages_CreateTiles chooseTileImages_createTiles;
    private static ShowTileImages showTileImages;

}
