package model.dto;

import java.io.Serializable;

public class UnlockBoxDTO implements Serializable {
    String boxId;

    public UnlockBoxDTO(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxId() {
        return boxId;
    }

    @Override
    public String toString() {
        return "UnlockBoxDTO{" +
                "boxId='" + boxId + '\'' +
                '}';
    }
}
