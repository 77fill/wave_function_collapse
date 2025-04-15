package dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import processing.core.PVector;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ViewModel {
    volatile Scene currentScene;
    volatile PVector sceneUpperLeft;
    public enum Scene {
        None, ImagesGrid
    }
}
