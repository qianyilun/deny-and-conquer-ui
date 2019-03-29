package model.status.game;

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
}
