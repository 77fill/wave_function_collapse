package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystem_TempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoadResources_IntoTempDirectory {
    @Autowired
    private ResourceStore resources;
    @Autowired
    private FileSystem_TempDirectory tempDir;
    @Autowired
    private View view;

    @Async("background")
    public void run() {

        if(tempDir.unavailable())
            try {
                tempDir.create();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }


        resources.all().forEach(tempDir::insert);

       this.notifyView();
    }

    @Async("display")
    private void notifyView() {
        view.tempDirectoryLoaded();
    }

}
