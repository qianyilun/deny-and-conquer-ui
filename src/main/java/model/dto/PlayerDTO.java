package model.dto;

import model.LocalStatus;

import java.awt.*;
import java.io.Serializable;

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

    public String getPlayerIP() {
        return playerIP;
    }

    public void setPlayerIP(String playerIP) {
        this.playerIP = playerIP;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
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
                ", isServer=" + isServer +
                ", playerId=" + playerId +
                '}';
    }
}
