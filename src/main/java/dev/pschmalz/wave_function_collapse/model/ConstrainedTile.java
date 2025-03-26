package dev.pschmalz.wave_function_collapse.model;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class ConstrainedTile {
    private Tile tile;
    private List<Constraint> constraints = new ArrayList<>();

    public ConstrainedTile(PImage img) {
        tile = new Tile(img);
    }

    public ConstrainedTile(Tile tile) {
        this.tile = tile;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }
}
