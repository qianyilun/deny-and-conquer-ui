package business;

import facade.ServerManager;
import model.ConfigurationDTO;
import model.PlayerDTO;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class LoginService {
    /**
     * register the new client to server with player's information
     * with TCP socket
     *
     * @return
     */
    public boolean retrieveGameConfigurationFromServer(String name, Color color) throws IOException, ClassNotFoundException {
        InetAddress addr = InetAddress.getLocalHost();
        PlayerDTO playerDTO = new PlayerDTO(addr.getHostAddress(), name, color);

        // TODO: move to independent class
        InetAddress serverIp = InetAddress.getByName("192.168.0.13");


        Socket clientSocket = new Socket("192.168.0.13", 7777);
        System.out.println("Connected!");


        // get the output stream from the socket.
        OutputStream outputStream = clientSocket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(playerDTO);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        ConfigurationDTO configurationDTO = (ConfigurationDTO) objectInputStream.readObject();

        System.out.println(configurationDTO);

        return true;
    }

    /**
     * 1. create a game "room"
     * 2. creator as a host
     * 3. creator runs a server
     *
     * @return
     */
    public boolean launchGameConfigurationWorker(String hostName, int numOfPlayers, int thickness, int row) throws IOException {
        ServerManager.launch(numOfPlayers, thickness, row);
        return true;
    }
}
