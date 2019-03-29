package model.dto;

import model.LocalStatus;

import java.awt.*;
import java.io.Serializable;

/**
 * This class is used to transfer the box id as an object
 *
 * When: a box is fully colored, this DTO will be transferred to the server side
 */
public class ColoredBoxDTO implements Serializable {
    String boxId;
    Color color;

    public ColoredBoxDTO(String boxId) {
        this.boxId = boxId;
        color = LocalStatus.getInstance().getColor();
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
