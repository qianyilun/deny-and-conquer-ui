package ui.register.model;

import javafx.scene.canvas.Canvas;

import java.util.Objects;

public class BoxModel {
    private double coloredArea;
    private double boxArea;
    private double boxX;
    private double boxY;
    private boolean isColored;
    private boolean isLocked;
    private Canvas canvas;

    public BoxModel(double boxArea) {
        this.boxArea = boxArea;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public double getBoxX() {
        return boxX;
    }

    public void setBoxX(double boxX) {
        this.boxX = boxX;
    }

    public double getBoxY() {
        return boxY;
    }

    public void setBoxY(double boxY) {
        this.boxY = boxY;
    }

    public double getColoredArea() {
        return coloredArea;
    }

    public void setColoredArea(double coloredArea) {
        this.coloredArea = coloredArea;
    }

    public void addColoredArea(double width) {
        coloredArea += (width*width);
    }

    public double getBoxArea() {
        return boxArea;
    }

    public void setBoxArea(double boxArea) {
        this.boxArea = boxArea;
    }

    public boolean isColored() {
        return isColored;
    }

    public void setColored(boolean colored) {
        isColored = colored;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxModel boxModel = (BoxModel) o;
        return Double.compare(boxModel.coloredArea, coloredArea) == 0 &&
                Double.compare(boxModel.boxArea, boxArea) == 0 &&
                Double.compare(boxModel.boxX, boxX) == 0 &&
                Double.compare(boxModel.boxY, boxY) == 0 &&
                isColored == boxModel.isColored &&
                isLocked == boxModel.isLocked &&
                Objects.equals(canvas, boxModel.canvas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coloredArea, boxArea, boxX, boxY, isColored, isLocked, canvas);
    }

    @Override
    public String toString() {
        return "BoxModel{" +
                "coloredArea=" + coloredArea +
                ", boxArea=" + boxArea +
                ", boxX=" + boxX +
                ", boxY=" + boxY +
                ", isColored=" + isColored +
                ", isLocked=" + isLocked +
                ", canvas=" + canvas +
                '}';
    }
}
