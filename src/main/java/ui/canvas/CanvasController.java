package ui.canvas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Status;
import ui.register.BoxModel;
import ui.register.CanvasModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CanvasController {
    private CanvasModel canvasModel;
    private BoxModel currentBoxModel;

    public Text playerNameLabel;
    public Text playerRankLabel;
    public ProgressBar progressBar;
    public Label progressLabel;
    public ColorPicker colorPicker;
    public Slider penThickness;
    public TextArea boxRow;
    public TextArea boxColumn;
    public Button readyBtn;
    public Button startBtn;
    public Label progressPercentLabel;
    public Text playerMachineLabel;
    public TextArea boxPercentToColor;
    public Label penSettingLabel;
    public Button setCanvasBtn;
    public GridPane canvasGridPane;
    public Canvas canvasTest;

    private double boxArea = 0;

    public void onSetCanvasBtnClicked(ActionEvent event) {
        if (Status.getInstance().isInGame()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Canvas cannot be changed once you submitted.", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() != ButtonType.YES) {
            return;
        }

        try {
            int row = Integer.parseInt(boxRow.getText());
            int column = Integer.parseInt(boxColumn.getText());
            int percent = Integer.parseInt(boxPercentToColor.getText());

            if (row >= 1 && column >= 1 && percent >= 1 && percent <= 100) {
                canvasModel = new CanvasModel(row, column, percent);
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
            boxColumn.clear();
            boxPercentToColor.clear();
        }
    }

    private void updateCanvasGridPanel(CanvasModel canvasModel) {
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

        boxArea = width * height;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Canvas canvas = new Canvas();
                canvas.setId("canvas" + i  + "-" + j);

                canvas.autosize();

                canvas.setWidth(width);
                canvas.setHeight(height);

                drawCanvas(canvas);

                canvasModel.getBoxes().add(new BoxModel(boxArea));

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
                        currentBoxModel = determineCurrentBoxModel(event);
                        graphicsContext.beginPath();
                        graphicsContext.moveTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {

//                        graphicsContext.lineTo(event.getX(), event.getY());
                        graphicsContext.setFill(Color.BLACK);
                        graphicsContext.fillRect(event.getX(), event.getY(), 1, 1);

                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(BoxModel.arr);
                    }
                });
    }

    public void onReadyClicked(ActionEvent event) {
        if (Status.getInstance().isInGame()) {
            return;
        }

        String textOnBtn = readyBtn.getText();
        if (textOnBtn.equals("Ready")) {
            colorPicker.setDisable(true);
            penThickness.setDisable(true);
            boxRow.setDisable(true);
            boxColumn.setDisable(true);
            boxPercentToColor.setDisable(true);
            setCanvasBtn.setDisable(true);
            readyBtn.setText("Cancel");
        } else {
            colorPicker.setDisable(false);
            penThickness.setDisable(false);
            boxRow.setDisable(false);
            boxColumn.setDisable(false);
            boxPercentToColor.setDisable(false);
            setCanvasBtn.setDisable(false);
            readyBtn.setText("Ready");
        }

    }

    private void disableCanvasSettings() {
        boxRow.setDisable(true);
        boxColumn.setDisable(true);
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

//        gc.getPixelWriter().setColor();
    }

    private BoxModel determineCurrentBoxModel(MouseEvent event) {
        return canvasModel.getBoxes().get(0);
    }
}
