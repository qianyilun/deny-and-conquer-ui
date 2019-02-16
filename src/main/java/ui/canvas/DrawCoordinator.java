package ui.canvas;

import java.util.Objects;

public class DrawCoordinator {
    private double x = 0;
    private double y = 0;

    public DrawCoordinator(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawCoordinator that = (DrawCoordinator) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
