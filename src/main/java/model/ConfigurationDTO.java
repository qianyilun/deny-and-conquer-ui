package model;

import java.awt.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * model to represent the data form received from server (host player).
 *
 * server will send different "host" data package to different client
 */
public class ConfigurationDTO implements Serializable {
    private List<Socket> socketList;

    private double thickness;

    private int rows;

    public ConfigurationDTO(List<Socket> socketList, double thickness, int rows) {
        this.socketList = socketList;
        this.thickness = thickness;
        this.rows = rows;
    }

    public List<Socket> getSocketList() {
        return socketList;
    }

    public void setSocketList(List<Socket> socketList) {
        this.socketList = socketList;
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
        return "ConfigurationDTO{" +
                "socketList=" + socketList +
                ", thickness=" + thickness +
                ", rows=" + rows +
                '}';
    }
}
