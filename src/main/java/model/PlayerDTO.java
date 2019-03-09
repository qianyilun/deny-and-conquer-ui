package model;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class PlayerDTO implements Serializable {
    private String playerIP;
    private String playerName;
    private Color playerColor;

    public PlayerDTO(String playerIP, String playerName, Color playerColor) {
        this.playerIP = playerIP;
        this.playerName = playerName;
        this.playerColor = playerColor;
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
