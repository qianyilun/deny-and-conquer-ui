package daemon;

import facade.ServiceManager;
import model.status.game.LocalStatus;
import model.dto.ColoredBoxDTO;
import model.dto.ConfigurationDTO;
import model.dto.PlayerDTO;
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
                System.out.println("color the box " + coloredBoxDTO.getBoxId() + " by color " + coloredBoxDTO.getColor());
                ServiceManager.getGameService().colorBoxWithBoxId(coloredBoxDTO);
            }
        }
    }

    public void sendUpdatedGameStatusToClient() {
        System.out.println("Can you see");
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadId);
            t.start();
        }
    }
}
