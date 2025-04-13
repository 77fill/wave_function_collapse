package dev.pschmalz.wave_function_collapse.infrastructure.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ViewModel {
    volatile Optional<Scene> currentScene = Optional.empty();
}
