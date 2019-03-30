package model.dto;

public class LockBoxDTO {
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
