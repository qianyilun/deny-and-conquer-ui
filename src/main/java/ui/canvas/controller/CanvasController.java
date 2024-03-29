package ui.canvas.controller;

import com.sun.security.ntlm.Server;
import facade.ServerManager;
import facade.ServiceManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.status.game.GameStatus;
import model.status.game.LocalStatus;
import ui.register.model.BoxModel;
import ui.register.model.CanvasModel;
import utils.ColorUtils;

import java.awt.*;

public class CanvasController {
    public Text playerNameLabel;
    public Text playerRankLabel;
    public ColorPicker colorPicker;
    public Text playerMachineLabel;
    public Label penSettingLabel;
    public GridPane canvasGridPane;
    public Canvas canvasTest;
    private CanvasModel canvasModel = GameStatus.getInstance().getCanvasModel();

    private boolean firstClickOnGrid = true;

    public void onSetCanvasBtnClicked(ActionEvent event) {
        drawCanvasGridPanel();

    }

    private void drawCanvasGridPanel() {
        // generate grids in gridPane
        int numRows = canvasModel.getRow();
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            canvasGridPane.getRowConstraints().add(rowConst);

            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numRows);
            canvasGridPane.getColumnConstraints().add(colConst);
        }

        // attach canvas into gridPane
        double width = canvasGridPane.getWidth()/numRows;

        double boxArea = width * width;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numRows; j++) {
                Canvas canvas = new Canvas();
                canvas.setId("id: " + i  + "-" + j);

                canvas.autosize();

                canvas.setWidth(width);
                canvas.setHeight(width);

                drawBoxes(canvas);

                BoxModel boxModel = new BoxModel(boxArea);
                boxModel.setCanvas(canvas);

                boxModel.setBoxX(j * width);
                boxModel.setBoxY(i * width);

                canvasModel.getBoxes().add(boxModel);

                canvasGridPane.add(canvas, i, j);
            }
        }
    }

    // https://stackoverflow.com/questions/43429251/how-to-draw-a-continuous-line-with-mouse-on-javafx-canvas
    private void drawBoxes(Canvas canvas) {
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        BoxModel currentBoxModel = determineCurrentBoxModel(event);

                        if (!currentBoxModel.getColor().equals(java.awt.Color.WHITE)) {
                            return;
                        }

                        if (!LocalStatus.getInstance().isHost()) {
                            ServiceManager.getGameService().questionServerIsBoxLocked(currentBoxModel);
                        }

                        if (currentBoxModel.isLocked()) {
                            return;
                        }
                        if (!LocalStatus.getInstance().isHost()) {
                            ServiceManager.getGameService().sendLockBoxWithBoxIdCommandToServer(currentBoxModel);
                        } else {
                            ServiceManager.getGameService().sendLockBoxCommandToAllClients(currentBoxModel);
                        }
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        colorPixels(event, graphicsContext);
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        BoxModel currentBoxModel = determineCurrentBoxModel(event);

                        if (currentBoxModel.getColoredArea() >= (double) canvasModel.getPenThickness() / 100 * currentBoxModel.getBoxArea() ) {
                            // colored it all when area is enough
                            currentBoxModel.setColored(true);

                            graphicsContext.setFill(ColorUtils.toFxColor(canvasModel.getColor()));
                            graphicsContext.fillRect(0, 0, Math.sqrt(currentBoxModel.getBoxArea()), Math.sqrt(currentBoxModel.getBoxArea()));

                            if (!LocalStatus.getInstance().isHost()) {
                                ServiceManager.getGameService().sendColorBoxWithBoxIdCommandToServer(currentBoxModel);
                            } else {
                                ServiceManager.getGameService().sendRedrawBoxCommandToAllClients(currentBoxModel);
                            }
                        } else {
                            // color it back to white
                            currentBoxModel.setLocked(false);

                            graphicsContext.setFill(ColorUtils.toFxColor(currentBoxModel.getColor()));
                            graphicsContext.fillRect(0, 0, Math.sqrt(currentBoxModel.getBoxArea()), Math.sqrt(currentBoxModel.getBoxArea()));
                            initDraw(graphicsContext);

                            if (!currentBoxModel.getColor().equals(java.awt.Color.WHITE)) {
                                return;
                            }

                            if (!LocalStatus.getInstance().isHost()) {
                                ServiceManager.getGameService().sendUnlockBoxWithBoxIdCommandToServer(currentBoxModel);
                            } else {
                                ServiceManager.getGameService().sendUnlockBoxCommandToAllClients(currentBoxModel);
                            }
                        }
                    }
                });
    }

    private boolean isWithinCurrentBox(BoxModel currentBoxModel, MouseEvent event) {
        double x = event.getX() + currentBoxModel.getBoxX();
        double y = event.getY() + currentBoxModel.getBoxY();

        return x >= currentBoxModel.getBoxX() && x <= currentBoxModel.getBoxX() + currentBoxModel.getCanvas().getWidth()
                && y >= currentBoxModel.getBoxY() && y <= currentBoxModel.getBoxY() + currentBoxModel.getCanvas().getHeight();
    }

    private void colorPixels(MouseEvent event, GraphicsContext graphicsContext) {
        BoxModel currentBoxModel = determineCurrentBoxModel(event);
        if (!isWithinCurrentBox(currentBoxModel, event)) {
            return;
        }
        if (isPixelColored()) {
            return;
        }

        int penThickness = canvasModel.getPenThickness();
        currentBoxModel.addColoredArea(penThickness);
        graphicsContext.setFill(ColorUtils.toFxColor(canvasModel.getColor())/*colorPicker.getValue()*/);
        graphicsContext.fillRect(event.getX()-penThickness, event.getY()-penThickness, 2*penThickness, 2*penThickness);
    }

    private java.awt.Color getPixelColor() {
        // https://stackoverflow.com/questions/13061122/getting-rgb-value-from-under-mouse-cursor
        Robot robot = null;
        try {
            robot = new Robot();
            PointerInfo pi = MouseInfo.getPointerInfo();
            Point point = pi.getLocation();
            java.awt.Color awtColor = robot.getPixelColor(point.x, point.y);
            return awtColor;
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return java.awt.Color.WHITE;
    }

    private boolean isPixelColored() {
        java.awt.Color awtColor = getPixelColor();
        if (!awtColor.equals(java.awt.Color.WHITE)) {
            return true;
        }

        return false;
    }

    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);

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

    public void initialize(){
        canvasGridPane.setOnMouseClicked(event -> {
            if (firstClickOnGrid) {
                drawCanvasGridPanel();
                firstClickOnGrid = false;
            }
        });
    }
}
