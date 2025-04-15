package dev.pschmalz.wave_function_collapse.infrastructure.view;

import lombok.experimental.Delegate;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Use relative coordinates inside <code>Subclass#relativeDraw()</code><br>
 * Point of reference: return value of <code>Subclass#getUpperLeft()</code><br>
 * <br>
 * Use PApplet methods inside subclasses due to @Delegate PApplet ...<br>
 * Excluded methods can be found in <code>RelativeElement#LombokDelegateExclusion</code>
 */
public abstract class RelativeElement {

    public void draw() {
        pushMatrix();
        translate(getUpperLeft().x, getUpperLeft().y);

        relativeDraw();

        popMatrix();
    }

    abstract protected void relativeDraw();
    abstract public PVector getUpperLeft();
    @Delegate(excludes = LombokDelegateExclusion.class)
    abstract protected PApplet getPApplet();

    private interface LombokDelegateExclusion {
        void background(int color);
    }
}
