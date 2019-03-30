package daemon;

import business.GameService;
import facade.ServiceManager;
import model.dto.ColoredBoxDTO;
import model.dto.LockBoxDTO;
import model.dto.QueryBoxLockingDTO;
import model.dto.UnlockBoxDTO;
import model.status.game.LocalStatus;
import utils.SocketIOUtils;

import java.net.Socket;

public class ClientWorker implements Runnable {
    private Thread t;

    @Override
    public void run() {
        Socket socket = LocalStatus.socketBetweenThisMachineAndServer;
        while (true) {
            Object object = SocketIOUtils.readObjectFromSocket(socket);

            if (object.getClass().equals(ColoredBoxDTO.class)) {
                ServiceManager.getGameService().recolorBox((ColoredBoxDTO) object);
            } else if (object.getClass().equals(LockBoxDTO.class)) {
                ServiceManager.getGameService().lockBox((LockBoxDTO) object);
            } else if (object.getClass().equals(UnlockBoxDTO.class)) {
                ServiceManager.getGameService().unlockBox((UnlockBoxDTO) object);
            } else if (object.getClass().equals(QueryBoxLockingDTO.class)){
                QueryBoxLockingDTO queryBoxLockingDTO = (QueryBoxLockingDTO) object;
                ServiceManager.getGameService().lockOrUnlockBox(queryBoxLockingDTO.getBoxId(), queryBoxLockingDTO.isLocked());
            }
        }
    }

    public void listenForGameUpdatesFromServer() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
