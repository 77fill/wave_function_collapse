package dev.pschmalz.wave_function_collapse.infrastructure.gui.view;

import processing.core.PVector;

public interface MouseAwareElement {
    /**
     * Must check if inside element bounds!<br>
     * Reference point: upper left corner of element
     */
    default void mouseClicked(PVector relativeMousePosition) {}
}
