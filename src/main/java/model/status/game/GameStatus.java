package model.status.game;

import ui.register.model.CanvasModel;

/**
 * This class is for save all status in the middle of the game (canvas UI)
 */

public class GameStatus {
    private static GameStatus ourInstance = new GameStatus();

    public static GameStatus getInstance() {
        return ourInstance;
    }

    private GameStatus() {
    }

    private CanvasModel canvasModel;

    public synchronized CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public synchronized void setCanvasModel(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }
}
