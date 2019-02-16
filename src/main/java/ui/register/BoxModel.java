package ui.register;

import ui.canvas.DrawCoordinator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoxModel {
    public static List<Double> arr = new ArrayList<>();
    private Set<Double> coloredRow;
    private Set<Double> coloredColumn;
    private double currentBoxArea;
    private double boxArea;

    public BoxModel(double boxArea) {
        this.coloredRow = new HashSet<>();
        this.coloredColumn = new HashSet<>();
        this.currentBoxArea = 0;
        this.boxArea = boxArea;
    }

    public Set<Double> getColoredRow() {
        return coloredRow;
    }

    public void setColoredRow(Set<Double> coloredRow) {
        this.coloredRow = coloredRow;
    }

    public Set<Double> getColoredColumn() {
        return coloredColumn;
    }

    public void setColoredColumn(Set<Double> coloredColumn) {
        this.coloredColumn = coloredColumn;
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

    public boolean isColored(double x, double y) {
        return coloredRow.contains(x) && coloredColumn.contains(y);
    }

    public void colorBoxXAndY(double x, double y) {
        coloredRow.add(x);
        coloredColumn.add(y);
    }
}
