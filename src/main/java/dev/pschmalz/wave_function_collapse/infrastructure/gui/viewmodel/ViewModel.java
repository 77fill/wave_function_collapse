package dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.Scene;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import processing.core.PVector;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ViewModel {
    volatile Option<Scene> currentScene = Option.none();
    volatile PVector sceneUpperLeft;
}
