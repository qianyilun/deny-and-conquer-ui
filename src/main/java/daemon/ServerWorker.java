package daemon;

import model.dto.ConfigurationDTO;
import model.dto.PlayerDTO;

import java.io.*;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadId);
            t.start();
        }
    }
}
