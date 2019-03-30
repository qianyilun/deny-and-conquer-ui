package business;

import daemon.ServerWorker;
import facade.ServiceManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.dto.LockBoxDTO;
import model.dto.QueryBoxLockingDTO;
import model.dto.UnlockBoxDTO;
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
        Socket socket = LocalStatus.socketBetweenThisMachineAndServer;
        String boxId = boxModel.getCanvas().getId();
        ColoredBoxDTO coloredBoxDTO = new ColoredBoxDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, coloredBoxDTO);
    }

    public void sendLockBoxWithBoxIdCommandToServer(BoxModel boxModel) {
        Socket socket = LocalStatus.socketBetweenThisMachineAndServer;
        String boxId = boxModel.getCanvas().getId();
        LockBoxDTO lockBoxDTO = new LockBoxDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, lockBoxDTO);
    }

    public void sendUnlockBoxWithBoxIdCommandToServer(BoxModel boxModel) {
        Socket socket = LocalStatus.socketBetweenThisMachineAndServer;
        String boxId = boxModel.getCanvas().getId();
        UnlockBoxDTO unlockBoxDTO = new UnlockBoxDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, unlockBoxDTO);
    }

    public synchronized void recolorBox(ColoredBoxDTO coloredBoxDTO) {
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
                boxModel.setColor(color);

                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setFill(ColorUtils.toFxColor(color));
                graphicsContext.fillRect(0, 0, Math.sqrt(boxModel.getBoxArea()), Math.sqrt(boxModel.getBoxArea()));

                break;
            }
        }
    }

    public void sendRedrawBoxCommandToAllClients(BoxModel boxModel) {
        String boxId = boxModel.getCanvas().getId();
        ColoredBoxDTO coloredBoxDTO = new ColoredBoxDTO(boxId);
        sendRedrawBoxCommandToAllClients(coloredBoxDTO);
    }

    public void sendRedrawBoxCommandToAllClients(ColoredBoxDTO coloredBoxDTO) {
        List<ServerWorker> serverWorkerList = ServerStatus.getInstance().getWorkerList();

        for (ServerWorker serverWorker : serverWorkerList) {
            serverWorker.sendUpdatedGameStatusToClient(coloredBoxDTO);
        }
    }

    public void sendLockBoxCommandToAllClients(BoxModel boxModel) {
        String boxId = boxModel.getCanvas().getId();
        LockBoxDTO lockBoxDTO = new LockBoxDTO(boxId);
        sendLockBoxCommandToAllClients(lockBoxDTO);
    }

    public void sendLockBoxCommandToAllClients(LockBoxDTO lockBoxDTO) {
        List<ServerWorker> serverWorkerList = ServerStatus.getInstance().getWorkerList();

        for (ServerWorker serverWorker : serverWorkerList) {
            serverWorker.sendUpdatedGameStatusToClient(lockBoxDTO);
        }
    }

    public void sendUnlockBoxCommandToAllClients(BoxModel boxModel) {
        String boxId = boxModel.getCanvas().getId();
        UnlockBoxDTO unlockBoxDTO = new UnlockBoxDTO(boxId);
        sendUnlockBoxCommandToAllClients(unlockBoxDTO);
    }

    public void sendUnlockBoxCommandToAllClients(UnlockBoxDTO unlockBoxDTO) {
        List<ServerWorker> serverWorkerList = ServerStatus.getInstance().getWorkerList();

        for (ServerWorker serverWorker : serverWorkerList) {
            serverWorker.sendUpdatedGameStatusToClient(unlockBoxDTO);
        }
    }

    public void lockBox(LockBoxDTO lockBoxDTO) {
        String boxId = lockBoxDTO.getBoxId();

        lockOrUnlockBox(boxId, true);
    }

    public void unlockBox(UnlockBoxDTO unlockBoxDTO) {
        String boxId = unlockBoxDTO.getBoxId();

        lockOrUnlockBox(boxId, false);
    }

    public void lockOrUnlockBox(String boxId, boolean lockIt) {
        CanvasModel canvasModel = GameStatus.getInstance().getCanvasModel();

        List<BoxModel> boxModelList = canvasModel.getBoxes();
        for (BoxModel boxModel : boxModelList) {
            Canvas canvas = boxModel.getCanvas();
            if (boxId.equals(canvas.getId())) {
                boxModel.setLocked(lockIt);
                boxModel.setColored(lockIt);

                break;
            }
        }
    }


    public QueryBoxLockingDTO updateBoxLockingStatusByLocalStatus(QueryBoxLockingDTO queryBoxLockingDTO) {
        String boxId = queryBoxLockingDTO.getBoxId();
        // should send a request to server to ask if this box is locked
        CanvasModel canvasModel = GameStatus.getInstance().getCanvasModel();

        List<BoxModel> boxModelList = canvasModel.getBoxes();
        for (BoxModel boxModel : boxModelList) {
            Canvas canvas = boxModel.getCanvas();
            if (boxId.equals(canvas.getId())) {
                return new QueryBoxLockingDTO(boxId, boxModel.isLocked());
            }
        }
        return new QueryBoxLockingDTO(boxId, false);

    }

    public void questionServerIsBoxLocked(BoxModel boxModel) {
        Socket socket = LocalStatus.socketBetweenThisMachineAndServer;
        String boxId = boxModel.getCanvas().getId();
        QueryBoxLockingDTO queryBoxLockingDTO = new QueryBoxLockingDTO(boxId);

        SocketIOUtils.writeObjectToSocket(socket, queryBoxLockingDTO);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Object object = SocketIOUtils.readObjectFromSocket(socket);

//        System.out.println((QueryBoxLockingDTO) object);

//        return ((QueryBoxLockingDTO) object).isLocked();
    }
}
