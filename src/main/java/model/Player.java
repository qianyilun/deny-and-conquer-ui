package model;

import java.awt.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private InetAddress playerIP;
    private Color color;
    private List<Socket> socketList; // all players information, for backup, exclude host player's information
    private double thickness;
    private int rows;

    public Player(String name, InetAddress playerIP, Color color) {
        this.name = name;
        this.playerIP = playerIP;
        this.color = color;
    }

    public Player(String name, InetAddress playerIP, Color color, List<Socket> socketList, double thickness, int rows) {
        this.name = name;
        this.playerIP = playerIP;
        this.color = color;
        this.socketList = socketList;
        this.thickness = thickness;
        this.rows = rows;
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
                ", socketList=" + socketList +
                ", thickness=" + thickness +
                ", rows=" + rows +
                '}';
    }
}
