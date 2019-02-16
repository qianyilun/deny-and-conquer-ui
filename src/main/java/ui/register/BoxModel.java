package ui.register;

import ui.canvas.DrawCoordinator;

import java.util.HashSet;
import java.util.Set;

public class BoxModel {
    private Set<DrawCoordinator> coloredArea;
    private double currentBoxArea;
    private double boxArea;

    public BoxModel(double boxArea) {
        this.coloredArea = new HashSet<>();
        this.currentBoxArea = 0;
        this.boxArea = boxArea;
    }

    public Set<DrawCoordinator> getColoredArea() {
        return coloredArea;
    }

    public void setColoredArea(Set<DrawCoordinator> coloredArea) {
        this.coloredArea = coloredArea;
    }

    public double getCurrentBoxArea() {
        return currentBoxArea;
    }

    public void setCurrentBoxArea(double currentBoxArea) {
        this.currentBoxArea = currentBoxArea;
    }

    public void addColoredArea(double times) {
        currentBoxArea += times;
    }

    public double getBoxArea() {
        return boxArea;
    }

    public void setBoxArea(double boxArea) {
        this.boxArea = boxArea;
    }
}
