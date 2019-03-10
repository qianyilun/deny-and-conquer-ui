package business;

import facade.ServerManager;
import javafx.stage.Stage;
import model.ConfigurationDTO;
import model.GlobalStatus;
import model.LocalStatus;
import model.PlayerDTO;
import ui.canvas.MainCanvas;
import ui.register.Main;

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
    public boolean sendAndRetrieveGameConfigurationFromServer(String name, Color color) throws IOException, ClassNotFoundException {
        InetAddress addr = InetAddress.getLocalHost();
        PlayerDTO playerDTO = new PlayerDTO(addr.getHostAddress(), name, color);

        // TODO: move to independent class
        InetAddress serverIp = InetAddress.getByName("192.168.0.15");


        Socket clientSocket = new Socket("192.168.0.15", 7777);
        System.out.println("Connected!");


        // get the output stream from the socket.
        OutputStream outputStream = clientSocket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(playerDTO);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        ConfigurationDTO configurationDTO = (ConfigurationDTO) objectInputStream.readObject();

        System.out.println(configurationDTO);

        GlobalStatus.getInstance().setConfigurationDTO(configurationDTO);

        return true;
    }

    public boolean setLocalColorToLocalStatus(Color color) {
        LocalStatus.getInstance().setColor(color);
        return true;
    }

    /**
     * 1. create a game "room"
     * 2. creator as a host
     * 3. creator runs a server
     *
     * @return
     */
    public boolean launchGameConfigurationWorker(String hostName, int numOfPlayers, int thickness, int row, int percent) throws IOException {
        boolean received = ServerManager.launch(numOfPlayers, thickness, row, percent);
        if (received) {
            // game begin
            Stage stage = Main.getStage();
            stage.hide();
            MainCanvas.launchCanvas(stage);
        }
        return true;
    }
}
