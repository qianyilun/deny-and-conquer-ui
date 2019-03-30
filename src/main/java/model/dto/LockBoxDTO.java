package model.dto;

import java.io.Serializable;

public class LockBoxDTO implements Serializable {
    String boxId;

    public LockBoxDTO(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxId() {
        return boxId;
    }

    @Override
    public String toString() {
        return "LockBoxDTO{" +
                "boxId='" + boxId + '\'' +
                '}';
    }
}
