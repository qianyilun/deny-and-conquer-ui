package business;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.status.game.LocalStatus;
import model.dto.ColoredBoxDTO;
import ui.register.model.BoxModel;
import ui.register.model.CanvasModel;
import utils.ColorUtils;
import utils.SocketIOUtils;

import java.awt.Color;
import java.net.Socket;
import java.util.List;

public class GameService {
    public void sendColorBoxWithBoxIdCommandToServer(BoxModel boxModel) {
        Socket socket = LocalStatus.getInstance().getSocketBetweenThisMachineAndServer();
        String boxId = boxModel.getCanvas().getId();
        ColoredBoxDTO coloredBoxDTO = new ColoredBoxDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, coloredBoxDTO);
    }

    public void colorBoxWithBoxId(ColoredBoxDTO coloredBoxDTO) {
        String boxId = coloredBoxDTO.getBoxId();
        Color color = coloredBoxDTO.getColor();

        CanvasModel canvasModel = LocalStatus.getInstance().getCanvasModel();
        List<BoxModel> boxModelList = canvasModel.getBoxes();
        for (BoxModel boxModel : boxModelList) {
            Canvas canvas = boxModel.getCanvas();
            if (boxId.equals(canvas.getId())) {
                System.out.println("find the box");

                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setFill(ColorUtils.toFxColor(color));
                graphicsContext.fillRect(0, 0, Math.sqrt(boxModel.getBoxArea()), Math.sqrt(boxModel.getBoxArea()));
            }
        }
    }
}
