package dev.pschmalz.wave_function_collapse.infrastructure.gui.util;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of bidirectional binding properties.<br><br>
 * Example:<br>
 * <code>view.button1.text.bind(viewmodel.coolText)</code><br>
 * where: text & coolText are Property < String ><br>
 * now you can change both values by using either <code>text#setValue()</code> or <code>coolText#setValue()</code>
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Property<T> {
    @Getter
    volatile T value;
    Option<Property<T>> bindingProperty = Option.none();
    Set<Property<T>> boundProperties = new HashSet<>();

    public void setValue(T value) {
        this.value = value;
        emitChangeEvent();
        bindingProperty.peek(prop -> prop.changedEvent(value));
    }

    public void bind(Property<T> otherProperty) {
        bindingProperty = Option.of(otherProperty);
        otherProperty.registerBoundProperty(this);
        changedEvent(otherProperty.getValue());
    }

    public void unbind() {
        bindingProperty.peek(prop -> prop.unregisterBoundProperty(this));
        bindingProperty = Option.none();
    }

    public void registerBoundProperty(Property<T> boundProperty) {
        boundProperties.add(boundProperty);
    }

    public void unregisterBoundProperty(Property<T> boundProperty) {
        boundProperties.remove(boundProperty);
    }

    private void emitChangeEvent() {
        boundProperties.forEach(prop -> prop.changedEvent(value));
    }

    private void changedEvent(T value) {
        this.value = value;
    }

    public Property(T value) {
        this.value = value;
    }
}
