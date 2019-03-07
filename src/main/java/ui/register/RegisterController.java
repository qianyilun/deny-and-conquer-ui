package ui.register;

import facade.InstanceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import utils.ColorUtils;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RegisterController {
    public TextArea playerNameText;
    public TextArea hostIPText;
    public Button connectBtn;
    public Text playerIPText;
    public ColorPicker clientColorPicker;

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
    }

    @FXML
    private void onConnectClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String playerName = playerNameText.getText();
        Color awtColor = ColorUtils.toAwtColor(clientColorPicker.getValue());
        boolean result = InstanceManager.getLoginService().registerToServer(playerName, awtColor);

        if (result) {
            System.out.println("The window will be closed");
        }
    }

}
