package facade;

import business.GameService;
import business.LoginService;

/**
 * manage all instances in the business package
 */

public class ServiceManager {
    private static LoginService loginService = new LoginService();

    private static GameService gameService = new GameService();

    public static LoginService getLoginService() {
        return loginService;
    }

    public static GameService getGameService() {
        return gameService;
    }

    private ServiceManager() {
    }
}
