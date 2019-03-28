package model;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class PlayerDTO implements Serializable {
    private String playerIP;
    private String playerName;
    private Color playerColor;
    private boolean isServer;
    private int playerId;

    public PlayerDTO(String playerIP, String playerName, Color playerColor) {
        this.playerIP = playerIP;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.isServer = LocalStatus.getInstance().isHost();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "playerIP='" + playerIP + '\'' +
                ", playerName='" + playerName + '\'' +
                ", playerColor=" + playerColor +
                '}';
    }
}
