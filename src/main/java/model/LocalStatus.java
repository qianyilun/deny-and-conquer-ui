package model;

import ui.register.CanvasModel;

public class LocalStatus {
    private static LocalStatus ourInstance = new LocalStatus();
    private boolean isInGame = false;
    private boolean isHost = false;
    private CanvasModel canvasModel;

    public static LocalStatus getInstance() {
        return ourInstance;
    }

    private LocalStatus() {
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public void setCanvasModel(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public static LocalStatus getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(LocalStatus ourInstance) {
        LocalStatus.ourInstance = ourInstance;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
