package daemon;

import facade.ServerManager;
import facade.ServiceManager;
import model.dto.*;
import model.status.game.LocalStatus;
import ui.register.model.BoxModel;
import utils.SocketIOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable {
    private Thread t;
    private String threadId;
    private Socket socket; // the socket that this worker currently connects to
    private List<PlayerDTO> playerDTOS; // all players information, for backup, exclude configurationDTO player's information
    private int thickness;
    private int row;
    private int percent;
    private ConfigurationDTO configurationDTO;

    public ServerWorker(int threadId, Socket socket, List<PlayerDTO> playerDTOS, int thickness, int row, int percent) {
        this.threadId = "Thread-" + threadId;
        this.socket = socket;
        this.playerDTOS = playerDTOS;
        this.thickness = thickness;
        this.row = row;
        this.percent = percent;

        init();
    }

    private void init() {
        configurationDTO = new ConfigurationDTO(playerDTOS, thickness, row, percent);
    }



    @Override
    public void run() {
        OutputStream outputStream;
        try {
            outputStream = socket.getOutputStream();
            ObjectOutputStream objectInputStream = new ObjectOutputStream(outputStream);
            objectInputStream.writeObject(configurationDTO);

            launchGameStatusReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void launchGameStatusReceiver() throws IOException {
        while (true) {
            Object object = SocketIOUtils.readObjectFromSocket(socket);

            if (object.getClass().equals(ColoredBoxDTO.class)) {
                ColoredBoxDTO coloredBoxDTO = (ColoredBoxDTO) object;
                ServiceManager.getGameService().recolorBox(coloredBoxDTO);

                // notify all clients game status need to update
                ServiceManager.getGameService().sendRedrawBoxCommandToAllClients(coloredBoxDTO);
            } else if (object.getClass().equals(LockBoxDTO.class)) {
                LockBoxDTO lockBoxDTO = (LockBoxDTO) object;
                ServiceManager.getGameService().lockBox(lockBoxDTO);

                System.out.println("Lock the box " + lockBoxDTO);

                // notify all clients game status need to update
                ServiceManager.getGameService().sendLockBoxCommandToAllClients(lockBoxDTO);
            } else if (object.getClass().equals(UnlockBoxDTO.class)) {
                UnlockBoxDTO unlockBoxDTO = (UnlockBoxDTO) object;
                ServiceManager.getGameService().unlockBox(unlockBoxDTO);

                System.out.println("Unlock the box " + unlockBoxDTO);

                // notify all clients game status need to update
                ServiceManager.getGameService().sendUnlockBoxCommandToAllClients(unlockBoxDTO);
            } else if (object.getClass().equals(QueryBoxLockingDTO.class)) {
                QueryBoxLockingDTO queryBoxLockingDTO = (QueryBoxLockingDTO) object;
                QueryBoxLockingDTO result = ServiceManager.getGameService().updateBoxLockingStatusByLocalStatus(queryBoxLockingDTO);

                SocketIOUtils.writeObjectToSocket(socket, result);
            }
        }
    }

    public void sendUpdatedGameStatusToClient(Object object) {
        SocketIOUtils.writeObjectToSocket(socket, object);
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadId);
            t.start();
        }
    }
}
