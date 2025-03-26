package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;

import java.io.IOException;
import java.util.concurrent.Executor;

public class LoadResources_IntoTempDirectory {
    private ResourceStore resources;
    private FileSystem_TempDirectory tempDir;
    private Executor background, display;
    private View view;

    public void execute(View view) {
        this.view = view;
        background.execute(this::executeAndHandleExceptions);
    }

    private void executeAndHandleExceptions() {
        try {
            executeProper();
        } catch (IOException e) {

        }
    }

    private void executeProper() throws IOException {
        if(tempDir.unavailable())
            tempDir.create();

        resources.all().forEach(tempDir::insert);

        display.execute(view::tempDirectoryLoaded);
    }

    public LoadResources_IntoTempDirectory(ResourceStore resources, FileSystem_TempDirectory tempDir, Executor background, Executor display) {
        this.resources = resources;
        this.tempDir = tempDir;
        this.background = background;
        this.display = display;
    }
}
