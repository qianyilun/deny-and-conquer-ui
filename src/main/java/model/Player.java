package model;

import java.awt.*;
import java.io.Serializable;
import java.net.InetAddress;

public class Player implements Serializable {
    private String name;
    private InetAddress playerIP;
    private Color color;

    public Player(String name, InetAddress playerIP, Color color) {
        this.name = name;
        this.playerIP = playerIP;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getPlayerIP() {
        return playerIP;
    }

    public void setPlayerIP(InetAddress playerIP) {
        this.playerIP = playerIP;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", playerIP=" + playerIP +
                ", color=" + color +
                '}';
    }
}
