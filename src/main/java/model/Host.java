package model;

import java.awt.*;
import java.net.InetAddress;
import java.util.List;

/**
 * model to represent the data form received from server (host player).
 *
 * server will send different "host" data package to different client
 */
public class Host {
    private List<Player> playerList;

    private double thickness;

    private int rows;

    public Host(List<Player> playerList, double thickness, int rows) {
        this.playerList = playerList;
        this.thickness = thickness;
        this.rows = rows;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Host{" +
                "playerList=" + playerList +
                ", thickness=" + thickness +
                ", rows=" + rows +
                '}';
    }
}
