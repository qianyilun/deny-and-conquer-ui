package model;

import ui.register.model.BoxModel;

import java.util.List;

public class GameState {
    // Access restriction might change later on to protected/private
    public List<BoxModel> gameBoxes;

    public GameState(List<BoxModel> boxes) {
        this.gameBoxes = boxes;
    }

    public void updateGameBoxes(List<BoxModel> newBoxes) {
        this.gameBoxes = newBoxes;
    }

    public List<BoxModel> getGameBoxes() {
        return gameBoxes;
    }
}
