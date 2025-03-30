package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoadResources_IntoTempDirectory {
    private ResourceStore resources;
    private FileSystem_TempDirectory tempDir;
    private View view;

    @Async("background")
    public void execute(View view) {
        this.view = view;

        if(tempDir.unavailable())
            try {
                tempDir.create();
            } catch (IOException e) {

            }


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
