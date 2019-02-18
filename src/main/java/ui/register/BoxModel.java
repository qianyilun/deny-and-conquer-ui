package ui.register;

import javafx.scene.canvas.Canvas;

public class BoxModel {
    private double coloredArea;
    private double boxArea;
    private double boxX;
    private double boxY;
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

    public void addColoredArea(double times) {
        coloredArea += (times*times);
    }

    public double getBoxArea() {
        return boxArea;
    }

    public void setBoxArea(double boxArea) {
        this.boxArea = boxArea;
    }
}
