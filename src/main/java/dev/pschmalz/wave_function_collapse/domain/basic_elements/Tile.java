package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import io.vavr.collection.Set;
import lombok.Value;
import lombok.With;

@Value
public class Tile {
    Image image;
    @With
    Set<SmartConstraint> constraints;
}