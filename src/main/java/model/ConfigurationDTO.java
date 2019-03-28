package model;

import java.io.Serializable;
import java.util.List;

/**
 * model to represent the data form received from server (host player).
 *
 * server will send different "host" data package to different client
 */
public class ConfigurationDTO implements Serializable {
    private List<PlayerDTO> playerDTOList;

    private int thickness;
    private int rows;
    private int percent;

    public ConfigurationDTO(List<PlayerDTO> playerDTOList, int thickness, int rows, int percent) {
        this.playerDTOList = playerDTOList;
        this.thickness = thickness;
        this.rows = rows;
        this.percent = percent;
    }

    public List<PlayerDTO> getPlayerDTOList() {
        return playerDTOList;
    }

    public int getThickness() {
        return thickness;
    }

    public int getRows() {
        return rows;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "ConfigurationDTO{" +
                "playerDTOList=" + playerDTOList +
                ", thickness=" + thickness +
                ", rows=" + rows +
                ", percent=" + percent +
                '}';
    }
}
