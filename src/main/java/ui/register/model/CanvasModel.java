package ui.register.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CanvasModel {
    private static CanvasModel ourInstance = new CanvasModel();

    public static CanvasModel getInstance() {
        return ourInstance;
    }

    private CanvasModel() {
    }

    private int row;
    private int percent;
    private List<BoxModel> boxes;
    private int penThickness;
    private Color color;

    public void initFields(int row, int percent, int penThickness, Color color) {
        this.row = row;
        this.percent = percent;
        this.boxes = new ArrayList<>(row * row);
        this.penThickness = penThickness;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    private void setRow(int row) {
        this.row = row;
    }

    public int getPercent() {
        return percent;
    }

    private void setPercent(int percent) {
        this.percent = percent;
    }

    public List<BoxModel> getBoxes() {
        return boxes;
    }

    private void setBoxes(List<BoxModel> boxes) {
        this.boxes = boxes;
    }

    public int getPenThickness() {
        return penThickness;
    }

    private void setPenThickness(int penThickness) {
        this.penThickness = penThickness;
    }

    public Color getColor() {
        return color;
    }

    private void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "CanvasModel{" +
                "row=" + row +
                ", percent=" + percent +
                ", boxes=" + boxes +
                ", penThickness=" + penThickness +
                ", color=" + color +
                '}';
    }
}
