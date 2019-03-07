package facade;

import business.LoginService;

/**
 * manage all instances in the business package
 */

public class InstanceManager {
    private static LoginService loginService = new LoginService();

    public static LoginService getLoginService() {
        return loginService;
    }

    private InstanceManager() {
    }
}
