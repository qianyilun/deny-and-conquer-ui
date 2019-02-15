package ui.register;

public class CanvasModel {
    private int canvasRow;
    private int canvasColumn;
    private int canvasPercent;

    public CanvasModel(int canvasRow, int canvasColumn, int canvasPercent) {
        this.canvasRow = canvasRow;
        this.canvasColumn = canvasColumn;
        this.canvasPercent = canvasPercent;
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

    @Override
    public String toString() {
        return "CanvasModel{" +
                "canvasRow=" + canvasRow +
                ", canvasColumn=" + canvasColumn +
                ", canvasPercent=" + canvasPercent +
                '}';
    }
}
