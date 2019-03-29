package model;

public class GameStatus {
    private static GameStatus ourInstance = new GameStatus();

    public static GameStatus getInstance() {
        return ourInstance;
    }

    private GameStatus() {
    }
}
