package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.function.Function;

public class PositionInsideGrid extends PVector {
    private final PVector pVector;
    private final ImagesGridViewModel viewModel;

    public boolean isOverLowerEdge() {
        return viewModel.getLowerEdge() < pVector.y + viewModel.getSize();
    }

    public PositionInsideGrid toTheNextRow() {
        var newPos = new PVector(
                viewModel.getDistanceEdge(),
                pVector.y
                        + viewModel.getSize()
                        + viewModel.getDistanceBetween());

        return new PositionInsideGrid(newPos, viewModel);
    }

    public PositionInsideGrid toTheRight() {
        var newPos = new PVector(
                pVector.x
                        + viewModel.getSize()
                        + viewModel.getDistanceBetween(),
                pVector.y);
        return new PositionInsideGrid(newPos, viewModel);
    }

    public boolean isRightmost() {
        return viewModel.getRightEdge() < pVector.x + viewModel.getSize();
    }

    static public PositionInsideGrid firstPosition(ImagesGridViewModel viewModel) {
        return new PositionInsideGrid(
                new PVector(
                        viewModel.getDistanceEdge(),
                        viewModel.getDistanceEdge()
                ),
                viewModel
        );
    }

    // BUILDER PATTERN

    private PositionInsideGrid(PVector pVector, ImagesGridViewModel viewModel) {
        this.pVector = pVector.copy();
        this.viewModel = viewModel;
    }

    static Builder withGridViewModel(ImagesGridViewModel viewModel) {
        return pVector -> new PositionInsideGrid(pVector, viewModel);
    }

    interface Builder extends Function<PVector, PositionInsideGrid> {
        default PositionInsideGrid build(PVector pVector) {
            return apply(pVector);
        }
    }

    // DELEGATION

    @Override
    public PVector set(float x, float y, float z) {
        return pVector.set(x, y, z);
    }

    @Override
    public PVector set(float x, float y) {
        return pVector.set(x, y);
    }

    @Override
    public PVector set(PVector v) {
        return pVector.set(v);
    }

    @Override
    public PVector set(float[] source) {
        return pVector.set(source);
    }

    public static PVector random2D() {
        return PVector.random2D();
    }

    public static PVector random2D(PApplet parent) {
        return PVector.random2D(parent);
    }

    public static PVector random2D(PVector target) {
        return PVector.random2D(target);
    }

    public static PVector random2D(PVector target, PApplet parent) {
        return PVector.random2D(target, parent);
    }

    public static PVector random3D() {
        return PVector.random3D();
    }

    public static PVector random3D(PApplet parent) {
        return PVector.random3D(parent);
    }

    public static PVector random3D(PVector target) {
        return PVector.random3D(target);
    }

    public static PVector random3D(PVector target, PApplet parent) {
        return PVector.random3D(target, parent);
    }

    public static PVector fromAngle(float angle) {
        return PVector.fromAngle(angle);
    }

    public static PVector fromAngle(float angle, PVector target) {
        return PVector.fromAngle(angle, target);
    }

    @Override
    public PVector copy() {
        return pVector.copy();
    }

    @Override
    @Deprecated
    public PVector get() {
        return pVector.get();
    }

    @Override
    public float[] get(float[] target) {
        return pVector.get(target);
    }

    @Override
    public float mag() {
        return pVector.mag();
    }

    @Override
    public float magSq() {
        return pVector.magSq();
    }

    @Override
    public PVector add(PVector v) {
        return pVector.add(v);
    }

    @Override
    public PVector add(float x, float y) {
        return pVector.add(x, y);
    }

    @Override
    public PVector add(float x, float y, float z) {
        return pVector.add(x, y, z);
    }

    public static PVector add(PVector v1, PVector v2) {
        return PVector.add(v1, v2);
    }

    public static PVector add(PVector v1, PVector v2, PVector target) {
        return PVector.add(v1, v2, target);
    }

    @Override
    public PVector sub(PVector v) {
        return pVector.sub(v);
    }

    @Override
    public PVector sub(float x, float y) {
        return pVector.sub(x, y);
    }

    @Override
    public PVector sub(float x, float y, float z) {
        return pVector.sub(x, y, z);
    }

    public static PVector sub(PVector v1, PVector v2) {
        return PVector.sub(v1, v2);
    }

    public static PVector sub(PVector v1, PVector v2, PVector target) {
        return PVector.sub(v1, v2, target);
    }

    @Override
    public PVector mult(float n) {
        return pVector.mult(n);
    }

    public static PVector mult(PVector v, float n) {
        return PVector.mult(v, n);
    }

    public static PVector mult(PVector v, float n, PVector target) {
        return PVector.mult(v, n, target);
    }

    @Override
    public PVector div(float n) {
        return pVector.div(n);
    }

    public static PVector div(PVector v, float n) {
        return PVector.div(v, n);
    }

    public static PVector div(PVector v, float n, PVector target) {
        return PVector.div(v, n, target);
    }

    @Override
    public float dist(PVector v) {
        return pVector.dist(v);
    }

    public static float dist(PVector v1, PVector v2) {
        return PVector.dist(v1, v2);
    }

    @Override
    public float dot(PVector v) {
        return pVector.dot(v);
    }

    @Override
    public float dot(float x, float y, float z) {
        return pVector.dot(x, y, z);
    }

    public static float dot(PVector v1, PVector v2) {
        return PVector.dot(v1, v2);
    }

    @Override
    public PVector cross(PVector v) {
        return pVector.cross(v);
    }

    @Override
    public PVector cross(PVector v, PVector target) {
        return pVector.cross(v, target);
    }

    public static PVector cross(PVector v1, PVector v2, PVector target) {
        return PVector.cross(v1, v2, target);
    }

    @Override
    public PVector normalize() {
        return pVector.normalize();
    }

    @Override
    public PVector normalize(PVector target) {
        return pVector.normalize(target);
    }

    @Override
    public PVector limit(float max) {
        return pVector.limit(max);
    }

    @Override
    public PVector setMag(float len) {
        return pVector.setMag(len);
    }

    @Override
    public PVector setMag(PVector target, float len) {
        return pVector.setMag(target, len);
    }

    @Override
    public float heading() {
        return pVector.heading();
    }

    @Override
    @Deprecated
    public float heading2D() {
        return pVector.heading2D();
    }

    @Override
    public PVector setHeading(float angle) {
        return pVector.setHeading(angle);
    }

    @Override
    public PVector rotate(float theta) {
        return pVector.rotate(theta);
    }

    @Override
    public PVector lerp(PVector v, float amt) {
        return pVector.lerp(v, amt);
    }

    public static PVector lerp(PVector v1, PVector v2, float amt) {
        return PVector.lerp(v1, v2, amt);
    }

    @Override
    public PVector lerp(float x, float y, float z, float amt) {
        return pVector.lerp(x, y, z, amt);
    }

    public static float angleBetween(PVector v1, PVector v2) {
        return PVector.angleBetween(v1, v2);
    }

    @Override
    public String toString() {
        return pVector.toString();
    }

    @Override
    public float[] array() {
        return pVector.array();
    }

    @Override
    public boolean equals(Object obj) {
        return pVector.equals(obj);
    }

    @Override
    public int hashCode() {
        return pVector.hashCode();
    }
}
