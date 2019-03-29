package daemon;

import model.LocalStatus;
import model.dto.ColoredBoxDTO;
import model.dto.ConfigurationDTO;
import model.dto.PlayerDTO;
import utils.SocketIOUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Locale;

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

            launchGameStatusHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void launchGameStatusHandler() {
        while (true) {
            Socket socket = LocalStatus.getInstance().getSocketBetweenThisMachineAndServer();
            Object object = SocketIOUtils.readObjectFromSocket(socket);

            if (object.getClass().equals(ColoredBoxDTO.class.getName())) {
                ColoredBoxDTO coloredBoxDTO = (ColoredBoxDTO) object;
                System.out.println("color the box " + coloredBoxDTO.getBoxId() + " by color " + coloredBoxDTO.getColor());
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadId);
            t.start();
        }
    }
}
