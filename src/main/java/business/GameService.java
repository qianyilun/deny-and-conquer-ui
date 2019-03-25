package business;

import model.GameState;
import ui.register.model.BoxModel;

import java.util.List;

// Class is used for interacting with gameState instead of calling gameState directly
// Only one instance of class exists
// Also may be used for future type-tracking in-game
public class GameService {
    private static GameService ourInstance = new GameService();

    public static GameService getInstance() {
        return ourInstance;
    }

    private GameService() {
    }

    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(List<BoxModel> boxes) {
        GameState newState = new GameState(boxes);
        syncGameState(newState);
    }

    public void syncGameState(GameState newState) {
        this.gameState.updateGameBoxes(newState.getGameBoxes());
    }
}
