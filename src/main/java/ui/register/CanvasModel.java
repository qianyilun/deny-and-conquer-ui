package ui.register;

import java.util.ArrayList;
import java.util.List;

public class CanvasModel {
    private int canvasRow;
    private int canvasColumn;
    private int canvasPercent;
    private List<BoxModel> boxes;
    private double penThickness;

    public CanvasModel(int canvasRow, int canvasColumn, int canvasPercent, double penThickness) {
        this.canvasRow = canvasRow;
        this.canvasColumn = canvasColumn;
        this.canvasPercent = canvasPercent;
        boxes = new ArrayList<>(canvasRow * canvasColumn);
        this.penThickness = penThickness;
    }

    public double getPenThickness() {
        return penThickness;
    }

    public void setPenThickness(double penThickness) {
        this.penThickness = penThickness;
    }

    public int getCanvasRow() {
        return canvasRow;
    }

    public void setCanvasRow(int canvasRow) {
        this.canvasRow = canvasRow;
    }

    public int getCanvasColumn() {
        return canvasColumn;
    }

    public void setCanvasColumn(int canvasColumn) {
        this.canvasColumn = canvasColumn;
    }

    public int getCanvasPercent() {
        return canvasPercent;
    }

    public void setCanvasPercent(int canvasPercent) {
        this.canvasPercent = canvasPercent;
    }

    public List<BoxModel> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxModel> boxes) {
        this.boxes = boxes;
    }

    @Override
    public String toString() {
        return "CanvasModel{" +
                "canvasRow=" + canvasRow +
                ", canvasColumn=" + canvasColumn +
                ", canvasPercent=" + canvasPercent +
                '}';
    }
}