package model;

import ui.register.CanvasModel;

public class Status {
    private static Status ourInstance = new Status();
    private boolean isInGame = false;
    private boolean isHost = false;
    private CanvasModel canvasModel;

    public static Status getInstance() {
        return ourInstance;
    }

    private Status() {
    }

    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

    public void setCanvasModel(CanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public static Status getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Status ourInstance) {
        Status.ourInstance = ourInstance;
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
