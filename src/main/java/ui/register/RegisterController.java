package ui.register;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RegisterController {
    public TextArea playerNameText;
    public TextArea hostIPText;
    public Button connectBtn;
    public CheckBox hostCheckbox;
    public Label hostIPLabel;
    public Label playerNameLabel;
    public AnchorPane registerAnchorPanel;
    public Label playerIPLabel;
    public Text playerIPText;

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

    public void hostButtonClicked(ActionEvent event) {
        if (hostCheckbox.isSelected()) {
            hostIPText.setDisable(true);
        } else {
            hostIPText.setDisable(false);
        }
    }
}
