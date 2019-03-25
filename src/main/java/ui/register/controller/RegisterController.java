package ui.register.controller;

import facade.ServiceManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import utils.ColorUtils;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField playerNameText;
    public TextField hostIPText;
    public Button connectBtn;
    public Text playerIPText;
    public ColorPicker clientColorPicker;
    public TextField hostNameText;
    public TextField numOfPlayersText;
    public ChoiceBox thicknessChoiceBox;
    public Text playerIPText1;
    public TextField numOfRowText;
    public TextField percentageText;
    public ColorPicker serverColorPicker;

    public void initialRegisterPanel() {
        String playerIp = "unknown";
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            connectBtn.setDisable(true);
            e.printStackTrace();
        }

        playerIp = inetAddress == null ? playerIp : inetAddress.getHostAddress();
        playerIPText.setText(playerIp);
        playerIPText1.setText(playerIp);
    }

    @FXML
    private void onConnectButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String playerName = playerNameText.getText();
        String hostIP = hostIPText.getText();
        Color awtColor = ColorUtils.toAwtColor(clientColorPicker.getValue());
        boolean setLocalColor = ServiceManager.getLoginService().setLocalColorToLocalStatus(awtColor);
        boolean sendAndRetrieve = ServiceManager.getLoginService().sendAndRetrieveGameConfigurationFromServer(playerName, awtColor, hostIP);

        if (setLocalColor && sendAndRetrieve) {
            System.out.println("The window will be closed");
        }
    }

    @FXML
    private void onHostButtonClick(ActionEvent event) throws IOException {
        String hostName = hostNameText.getText();
        int numOfPlayer = Integer.parseInt(numOfPlayersText.getText());
        int thickness = (int) thicknessChoiceBox.getValue();
        int row = Integer.parseInt(numOfRowText.getText());
        int percent = Integer.parseInt(percentageText.getText());
        Color awtColor = ColorUtils.toAwtColor(serverColorPicker.getValue());
        ServiceManager.getLoginService().setLocalColorToLocalStatus(awtColor);
        ServiceManager.getLoginService().launchGameConfigurationWorker(hostName, numOfPlayer, thickness, row, percent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialRegisterPanel();

        thicknessChoiceBox.setItems(FXCollections.observableArrayList(
                1, 2, 5, 10
        ));
    }
}
