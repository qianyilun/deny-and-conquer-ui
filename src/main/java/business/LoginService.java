package business;

import facade.ServerManager;
import javafx.stage.Stage;
import model.ConfigurationDTO;
import model.GlobalStatus;
import model.LocalStatus;
import model.PlayerDTO;
import ui.canvas.MainCanvas;
import ui.register.Main;
import ui.register.model.CanvasModel;

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
    public boolean sendAndRetrieveGameConfigurationFromServer(String name, Color color, String hostIP) throws IOException, ClassNotFoundException {
        InetAddress addr = InetAddress.getLocalHost();
        PlayerDTO playerDTO = new PlayerDTO(addr.getHostAddress(), name, color);

        // TODO: move to independent class
        InetAddress serverIp = InetAddress.getByName("192.168.0.15");


        Socket clientSocket = new Socket(hostIP, 7777);
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

        prepareCanvasDataForClient();

        launchCanvas();

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
    public void launchGameConfigurationWorker(String hostName, int numOfPlayers, int thickness, int row, int percent) throws IOException {
        prepareCanvasDataForServer(hostName, thickness, row, percent);
        boolean received = ServerManager.launch(numOfPlayers, thickness, row, percent);
        if (received) {
            // game begin
            launchCanvas();
        }
//        return true;
    }

    private void launchCanvas() throws IOException {
        Stage stage = Main.getStage();
        stage.hide();
        MainCanvas.launchCanvas(stage);
    }

    public void prepareCanvasDataForServer(String hostName, int thickness, int row, int percent) {
        java.awt.Color color = LocalStatus.getInstance().getColor();
        CanvasModel.getInstance().initFields(row, percent, thickness, color);
    }

    private void prepareCanvasDataForClient() {
        ConfigurationDTO configurationDTO = GlobalStatus.getInstance().getConfigurationDTO();
        int row = configurationDTO.getRows();
        int percent = configurationDTO.getPercent();
        int thickness = configurationDTO.getThickness();
        java.awt.Color color = LocalStatus.getInstance().getColor();

        CanvasModel.getInstance().initFields(row, percent, thickness, color);
    }
}
