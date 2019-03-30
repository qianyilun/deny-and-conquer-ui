package model.status.game;

import ui.register.model.CanvasModel;

import java.awt.Color;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is used for save all necessary information of this particular player (e.g. Player Allen)
 */

public class LocalStatus {
    private static LocalStatus ourInstance = new LocalStatus();
    private boolean isInGame = false;
    private boolean isHost = false;

    private Color color;
    public static Socket socketBetweenThisMachineAndServer;


    public static LocalStatus getInstance() {
        return ourInstance;
    }

    private LocalStatus() {
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

}
