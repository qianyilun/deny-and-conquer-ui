package business;

import model.LocalStatus;
import model.dto.ColoredBoxDTO;
import ui.register.model.BoxModel;
import utils.SocketIOUtils;

import java.io.IOException;
import java.net.Socket;

public class GameService {
    public void sendColorBoxWithBoxIdCommandToServer(BoxModel boxModel) {
        Socket socket = LocalStatus.getInstance().getSocketBetweenThisMachineAndServer();
        String boxId = boxModel.getCanvas().getId();
        ColoredBoxDTO coloredBoxDTO = new ColoredBoxDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, coloredBoxDTO);
    }
}
