package business;

import daemon.ClientWorker;
import facade.ServerManager;
import facade.ServiceManager;
import javafx.stage.Stage;
import model.dto.ConfigurationDTO;
import model.status.game.GameStatus;
import model.status.game.LocalStatus;
import model.dto.PlayerDTO;
import model.status.server_client.ClientStatus;
import ui.canvas.MainCanvas;
import ui.register.Main;
import ui.register.model.CanvasModel;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

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
        InetAddress serverIp = InetAddress.getByName("192.168.0.16");
        Socket clientSocket = new Socket("192.168.0.16", 7777);

        // save the connection socket
        LocalStatus.getInstance().setSocketBetweenThisMachineAndServer(clientSocket);

        // write object.
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(playerDTO);

        // read object
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        ConfigurationDTO configurationDTO = (ConfigurationDTO) objectInputStream.readObject();

        // set values based on the received object
        setServerIPInGlobalStatus(configurationDTO);
        savePlayerDTOInStatus(configurationDTO);
        System.out.println(configurationDTO);

        ClientStatus.getInstance().setConfigurationDTO(configurationDTO);
        prepareCanvasDataForClient();

        // launch game UI
        launchCanvas();

        // listen
        ClientWorker clientWorker = new ClientWorker();
        clientWorker.listenForGameUpdatesFromServer();

        return true;
    }

    private void savePlayerDTOInStatus(ConfigurationDTO configurationDTO) {
        ClientStatus.getInstance().setPlayerDTOS(configurationDTO.getPlayerDTOList());
    }

    private void setServerIPInGlobalStatus(ConfigurationDTO configurationDTO) {
        List<PlayerDTO> playerDTOS = configurationDTO.getPlayerDTOList();
        for (PlayerDTO playerDTO : playerDTOS) {
            if (playerDTO.isServer()) {
                ClientStatus.getInstance().setServerIP(playerDTO.getPlayerIP());
            }
        }
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
    public void launchGameConfigurationWorker(String hostName, int numOfPlayers, int thickness, int row, int percent) throws IOException, ClassNotFoundException {
        PlayerDTO hostPlayerDTO = new PlayerDTO(InetAddress.getLocalHost().getHostAddress(), hostName, LocalStatus.getInstance().getColor());
        boolean received = ServerManager.launch(numOfPlayers, thickness, row, percent, hostPlayerDTO);
        if (received) {
            // game begin
            prepareCanvasDataForServer(hostName, thickness, row, percent);
            launchCanvas();
        }
    }

    private void launchCanvas() throws IOException {
        Stage stage = Main.getStage();
        stage.hide();
        MainCanvas.launchCanvas(stage);
    }

    public void prepareCanvasDataForServer(String hostName, int thickness, int row, int percent) {
        java.awt.Color color = LocalStatus.getInstance().getColor();
        CanvasModel canvasModel = new CanvasModel(row, percent, thickness, color);
        GameStatus.getInstance().setCanvasModel(canvasModel);
    }

    private void prepareCanvasDataForClient() {
        ConfigurationDTO configurationDTO = ClientStatus.getInstance().getConfigurationDTO();
        int row = configurationDTO.getRows();
        int percent = configurationDTO.getPercent();
        int thickness = configurationDTO.getThickness();
        java.awt.Color color = LocalStatus.getInstance().getColor();

        CanvasModel canvasModel = new CanvasModel(row, percent, thickness, color);
        GameStatus.getInstance().setCanvasModel(canvasModel);
    }
}
