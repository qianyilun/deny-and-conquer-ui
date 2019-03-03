package ui.canvas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Status;
import ui.register.BoxModel;
import ui.register.CanvasModel;
import ui.register.ColorUtils;

import java.awt.*;

public class CanvasController {
    private CanvasModel canvasModel;

    public Text playerNameLabel;
    public Text playerRankLabel;
    public ColorPicker colorPicker;
    public Slider penThickness;
    public TextArea boxRow;
    public Button readyBtn;
    public Button startBtn;
    public Text playerMachineLabel;
    public TextArea boxPercentToColor;
    public Label penSettingLabel;
    public Button setCanvasBtn;
    public GridPane canvasGridPane;
    public Canvas canvasTest;

    public void onSetCanvasBtnClicked(ActionEvent event) {
        if (Status.getInstance().isInGame()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING, "Canvas cannot be changed once you submitted.", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() != ButtonType.YES) {
            return;
        }

        try {
            int row = Integer.parseInt(boxRow.getText());
            int column = Integer.parseInt(boxRow.getText());
            int percent = Integer.parseInt(boxPercentToColor.getText());

            if (row >= 1 && column >= 1 && percent >= 1 && percent <= 100) {
                canvasModel = new CanvasModel(row, column, percent, 100);
                Status.getInstance().setCanvasModel(canvasModel);

                System.out.println("Canvas Model has been set to " + Status.getInstance().getCanvasModel());

                updateCanvasGridPanel(canvasModel);

                disableCanvasSettings();
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
            Alert error = new Alert(Alert.AlertType.ERROR, "Please enter valid canvas settings" + ButtonType.OK);
            error.showAndWait();
            boxRow.clear();
            boxRow.clear();
            boxPercentToColor.clear();
        }
    }

    private void updateCanvasGridPanel(CanvasModel canvasModel) {
        // set pen thickness
        canvasModel.setPenThickness(penThickness.getValue());

        // generate grids in gridPane
        int numRows = canvasModel.getCanvasRow();
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            canvasGridPane.getRowConstraints().add(rowConst);

        }

        int numCols = canvasModel.getCanvasColumn();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            canvasGridPane.getColumnConstraints().add(colConst);
        }

        // attach canvas into gridPane
        double width = canvasGridPane.getWidth()/numCols;
        double height = canvasGridPane.getHeight()/numRows;

        double boxArea = width * height;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Canvas canvas = new Canvas();
                canvas.setId("canvas" + i  + "-" + j);

                canvas.autosize();

                canvas.setWidth(width);
                canvas.setHeight(height);

                drawCanvas(canvas);

                BoxModel boxModel = new BoxModel(boxArea);
                boxModel.setCanvas(canvas);

                boxModel.setBoxX(j * width);
                boxModel.setBoxY(i * height);

                canvasModel.getBoxes().add(boxModel);

                canvasGridPane.add(canvas, i, j);
            }
        }
    }

    // https://stackoverflow.com/questions/43429251/how-to-draw-a-continuous-line-with-mouse-on-javafx-canvas
    private void drawCanvas(Canvas canvas) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        colorPixels(event, graphicsContext, canvasModel.getPenThickness());
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        BoxModel currentBoxModel = determineCurrentBoxModel(event);
                        if (currentBoxModel.getColoredArea() >= Double.parseDouble(boxPercentToColor.getText()) / 100 * currentBoxModel.getBoxArea() ) {
                            graphicsContext.setFill(colorPicker.getValue());
                            graphicsContext.fillRect(0, 0, Math.sqrt(currentBoxModel.getBoxArea()), Math.sqrt(currentBoxModel.getBoxArea()));
                        } else {
                            graphicsContext.setFill(Color.WHITE);
                            graphicsContext.fillRect(0, 0, Math.sqrt(currentBoxModel.getBoxArea()), Math.sqrt(currentBoxModel.getBoxArea()));
                        }
                        System.out.println(currentBoxModel.getBoxArea());
                        System.out.println(currentBoxModel.getCanvas().getId() + ": " + currentBoxModel.getColoredArea());
                    }
                });
    }

    private boolean isWithinCurrentBox(BoxModel currentBoxModel, MouseEvent event) {
        double x = event.getX() + currentBoxModel.getBoxX();
        double y = event.getY() + currentBoxModel.getBoxY();

        return x >= currentBoxModel.getBoxX() && x <= currentBoxModel.getBoxX() + currentBoxModel.getCanvas().getWidth()
                && y >= currentBoxModel.getBoxY() && y <= currentBoxModel.getBoxY() + currentBoxModel.getCanvas().getHeight();
    }

    private void colorPixels(MouseEvent event, GraphicsContext graphicsContext, double penThickness) {
        BoxModel currentBoxModel = determineCurrentBoxModel(event);
        if (!isWithinCurrentBox(currentBoxModel, event)) {
            return;
        }
        if (isPixelColored()) {
            return;
        }

        currentBoxModel.addColoredArea(penThickness);
        graphicsContext.setFill(colorPicker.getValue());
        graphicsContext.fillRect(event.getX()-penThickness, event.getY()-penThickness, 2*penThickness, 2*penThickness);
    }

    private boolean isPixelColored() {
        // https://stackoverflow.com/questions/13061122/getting-rgb-value-from-under-mouse-cursor
        try {
            Robot robot = new Robot();
            PointerInfo pi = MouseInfo.getPointerInfo();
            Point point = pi.getLocation();
            java.awt.Color awtColor = robot.getPixelColor(point.x, point.y);
            if (!awtColor.equals(java.awt.Color.WHITE)) {
                return true;
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    private void onReadyClicked(ActionEvent event) {
        System.out.println(ColorUtils.toAwtColor(colorPicker.getValue()));
        if (Status.getInstance().isInGame()) {
            return;
        }

        String textOnBtn = readyBtn.getText();
        if (textOnBtn.equals("Ready")) {
            colorPicker.setDisable(true);
            penThickness.setDisable(true);
            boxRow.setDisable(true);
            boxRow.setDisable(true);
            boxPercentToColor.setDisable(true);
            setCanvasBtn.setDisable(true);
            readyBtn.setText("Cancel");
        } else {
            colorPicker.setDisable(false);
            penThickness.setDisable(false);
            boxRow.setDisable(false);
            boxRow.setDisable(false);
            boxPercentToColor.setDisable(false);
            setCanvasBtn.setDisable(false);
            readyBtn.setText("Ready");
        }

    }

    private void disableCanvasSettings() {
        boxRow.setDisable(true);
        boxRow.setDisable(true);
        boxPercentToColor.setDisable(true);
        setCanvasBtn.setDisable(true);
    }

    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
    }

    private BoxModel determineCurrentBoxModel(MouseEvent event) {
        String canvasId = ((Canvas) event.getSource()).getId();
        for (BoxModel boxModel : canvasModel.getBoxes()) {
            if (canvasId.equals(boxModel.getCanvas().getId())) {
                return boxModel;
            }
        }
        return null;
    }
}
