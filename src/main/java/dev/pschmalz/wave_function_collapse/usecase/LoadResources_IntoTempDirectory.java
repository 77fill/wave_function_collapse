package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

public class LoadResources_IntoTempDirectory {
    private ResourceStore resources;
    private FileSystem_TempDirectory tempDir;
    private View view;

    @Async("background")
    private void execute(View view) throws IOException {
        this.view = view;

        if(tempDir.unavailable())
            tempDir.create();

        resources.all().forEach(tempDir::insert);

       this.notifyView();
    }

    @Async("display")
    private void notifyView() {
        view.tempDirectoryLoaded();
    }

    public LoadResources_IntoTempDirectory(ResourceStore resources, FileSystem_TempDirectory tempDir) {
        this.resources = resources;
        this.tempDir = tempDir;
    }
}
