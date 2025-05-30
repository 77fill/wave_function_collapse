package dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.Property;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.MouseAwareElement;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.RelativeElement;
import io.vavr.Function0;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import processing.core.PApplet;
import processing.core.PVector;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
public class Button extends RelativeElement implements MouseAwareElement {
    String text;
    Function0<Void> clickHandler;
    @Getter
    PVector upperLeft;
    @Getter
    @Setter
    @NonFinal
    PApplet pApplet;
    int width, height;
    Property<Boolean> active = new Property<>(false);

    @Override
    protected void relativeDraw() {
        if(active.getValue()) {
            fill(200);
            rect(0,0,width,height);
            fill(0);
            text(text, 5, height-5);
        } else {
            fill(100);
            rect(0,0,width,height);
            fill(0);
            text(text, 5, height-5);
        }
    }

    @Override
    public void mouseClicked(PVector relativeMousePosition) {
        if(!isInside(relativeMousePosition)) return;

        if(active.getValue())
            clickHandler.apply();
    }

    private boolean isInside(PVector relativeMousePosition) {
        return 0 <= relativeMousePosition.x && relativeMousePosition.x < width
                && 0 <= relativeMousePosition.y && relativeMousePosition.y < height;
    }

    public boolean isActive() {
        return active.getValue();
    }

    public void setActive(boolean active) {
        this.active.setValue(active);
    }

    public Property<Boolean> activeProperty() {
        return active;
    }
}
