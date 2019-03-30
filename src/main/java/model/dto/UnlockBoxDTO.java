package model.dto;

public class UnlockBoxDTO {
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
