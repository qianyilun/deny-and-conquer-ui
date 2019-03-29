package model;

import ui.register.model.CanvasModel;

import java.awt.Color;
import java.net.Socket;

public class LocalStatus {
    private static LocalStatus ourInstance = new LocalStatus();
    private boolean isInGame = false;
    private boolean isHost = false;
    private CanvasModel canvasModel;
    private Color color;
    private Socket socketBetweenThisMachineAndServer;

    public static LocalStatus getInstance() {
        return ourInstance;
    }

    private LocalStatus() {
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public void setCanvasModel(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public static LocalStatus getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(LocalStatus ourInstance) {
        LocalStatus.ourInstance = ourInstance;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Socket getSocketBetweenThisMachineAndServer() {
        return socketBetweenThisMachineAndServer;
    }

    public void setSocketBetweenThisMachineAndServer(Socket socketBetweenThisMachineAndServer) {
        this.socketBetweenThisMachineAndServer = socketBetweenThisMachineAndServer;
    }
}
