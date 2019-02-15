package ui.canvas;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import model.Status;
import ui.register.CanvasModel;

public class CanvasController {
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
    public Button updateCanvasBtn;
    public GridPane canvasGridPane;

    public void onUpdateCanvasBtnClicked(ActionEvent event) {
        if (Status.getInstance().isInGame()) {
            return;
        }

        try {
            int row = Integer.parseInt(boxRow.getText());
            int column = Integer.parseInt(boxColumn.getText());
            int percent = Integer.parseInt(boxPercentToColor.getText());

            if (row >= 1 && column >= 1 && percent >= 1 && percent <= 100) {
                CanvasModel canvasModel = new CanvasModel(row, column, percent);
                Status.getInstance().setCanvasModel(canvasModel);

                System.out.println("Canvas Model has been set to " + Status.getInstance().getCanvasModel());

                updateCanvasGridPanel(canvasModel);
            } else {
                throw new NumberFormatException();
            }



        } catch (NumberFormatException e) {
            e.printStackTrace();

            System.out.println("Please enter valid canvas settings");
            boxRow.clear();
            boxColumn.clear();
            boxPercentToColor.clear();
        }
    }

    private void updateCanvasGridPanel(CanvasModel canvasModel) {
        cleanCanvasGridPanel();

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
    }

    private void cleanCanvasGridPanel() {
        while(canvasGridPane.getRowConstraints().size() > 0){
            canvasGridPane.getRowConstraints().remove(0);
        }
        while(canvasGridPane.getColumnConstraints().size() > 0){
            canvasGridPane.getColumnConstraints().remove(0);
        }

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
            updateCanvasBtn.setDisable(true);
            readyBtn.setText("Cancel");
        } else {
            colorPicker.setDisable(false);
            penThickness.setDisable(false);
            boxRow.setDisable(false);
            boxColumn.setDisable(false);
            boxPercentToColor.setDisable(false);
            updateCanvasBtn.setDisable(false);
            readyBtn.setText("Ready");
        }

    }
}
