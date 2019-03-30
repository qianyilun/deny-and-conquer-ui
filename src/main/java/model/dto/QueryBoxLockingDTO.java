package model.dto;

public class QueryBoxLockingDTO {
    String boxId;
    boolean isLocked;

    public QueryBoxLockingDTO(String boxId) {
        this.boxId = boxId;
    }

    public QueryBoxLockingDTO(String boxId, boolean isLocked) {
        this.boxId = boxId;
        this.isLocked = isLocked;
    }

    public String getBoxId() {
        return boxId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String toString() {
        return "QueryBoxLockingDTO{" +
                "boxId='" + boxId + '\'' +
                ", isLocked=" + isLocked +
                '}';
    }
}
