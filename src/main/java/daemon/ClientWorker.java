package daemon;

import facade.ServiceManager;
import model.dto.ColoredBoxDTO;
import model.status.game.LocalStatus;
import utils.SocketIOUtils;

import java.net.Socket;

public class ClientWorker implements Runnable {
    private Thread t;
    private String threadId;

    @Override
    public void run() {
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

    public void listenForGameUpdatesFromServer() {
        if (t == null) {
            t = new Thread(this, threadId);
            t.start();
        }
    }
}
