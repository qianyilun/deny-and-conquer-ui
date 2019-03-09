package daemon;

import model.ConfigurationDTO;
import model.PlayerDTO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorker implements Runnable {
    private Thread t;
    private String threadName;
    private Socket socket; // the socket that this worker currently connects to
    private List<Socket> socketList; // all players information, for backup, exclude configurationDTO player's information
    private int thickness;
    private int row;
    private ConfigurationDTO configurationDTO;

    public ServerWorker(String threadName, Socket socket, List<Socket> socketList, int thickness, int row) {
        this.threadName = threadName;
        this.socket = socket;
        this.socketList = socketList;
        this.thickness = thickness;
        this.row = row;

        init();
    }

    private void init() {
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        for (Socket socket1 : socketList) {
            try {
                PlayerDTO playerDTO = parsePlayerDTOFromSocket(socket1);
                playerDTOList.add(playerDTO);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        configurationDTO = new ConfigurationDTO(playerDTOList, thickness, row);
    }

    private PlayerDTO parsePlayerDTOFromSocket(Socket socket1) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket1.getInputStream());
        PlayerDTO playerDTO = (PlayerDTO) objectInputStream.readObject();
        return playerDTO;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            // create a DataInputStream so we can read data from it.
            ObjectOutputStream objectInputStream = new ObjectOutputStream(outputStream);

            // read the content from the socket
            objectInputStream.writeObject(configurationDTO);

            System.out.println(configurationDTO);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
