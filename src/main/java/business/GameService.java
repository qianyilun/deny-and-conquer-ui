package business;

import daemon.ServerWorker;
import facade.ServiceManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.status.game.GameStatus;
import model.status.game.LocalStatus;
import model.dto.ColoredBoxDTO;
import model.status.server_client.ClientStatus;
import model.status.server_client.ServerStatus;
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

        // where to add ask client listen???
    }

    public synchronized void colorBoxWithBoxId(ColoredBoxDTO coloredBoxDTO) {
        String boxId = coloredBoxDTO.getBoxId();
        Color color = coloredBoxDTO.getColor();

        CanvasModel canvasModel = GameStatus.getInstance().getCanvasModel();
        List<BoxModel> boxModelList = canvasModel.getBoxes();
        for (BoxModel boxModel : boxModelList) {
            Canvas canvas = boxModel.getCanvas();
            if (boxId.equals(canvas.getId())) {
                System.out.println("find the box");
                boxModel.setColored(true);
                boxModel.setLocked(true);

                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setFill(ColorUtils.toFxColor(color));
                graphicsContext.fillRect(0, 0, Math.sqrt(boxModel.getBoxArea()), Math.sqrt(boxModel.getBoxArea()));
            }
        }
    }

    public void sendNewGameStatusToAllClients() {
        List<ServerWorker> serverWorkerList = ServerStatus.getInstance().getWorkerList();

        for (ServerWorker serverWorker : serverWorkerList) {
            serverWorker.sendUpdatedGameStatusToClient();
        }
    }

    public void listenForGameUpdatesFromServer() {
        Socket socket = LocalStatus.getInstance().getSocketBetweenThisMachineAndServer();
        while (true) {
            Object object = SocketIOUtils.readObjectFromSocket(socket);

            if (object.getClass().equals(ColoredBoxDTO.class)) {
                ColoredBoxDTO coloredBoxDTO = (ColoredBoxDTO) object;
                System.out.println("color the box " + coloredBoxDTO.getBoxId() + " by color " + coloredBoxDTO.getColor());
                ServiceManager.getGameService().colorBoxWithBoxId(coloredBoxDTO);
            }
        }
    }

//    public void updateCanvasModelInGameStatus(BoxModel currentBoxModel) {
//        CanvasModel canvasModel = GameStatus.getInstance().getCanvasModel();
//        canvasModel.getBoxes().get()
//    }
}
