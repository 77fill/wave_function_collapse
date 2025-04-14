package dev.pschmalz.wave_function_collapse.infrastructure.view;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ViewModel {
    volatile Option<Scene> currentScene = Option.none();
}
