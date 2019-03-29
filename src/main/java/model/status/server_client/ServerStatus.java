package model.status.server_client;

import java.net.ServerSocket;

/**
 * This class is for save all information about server side for server-client communication usage
 */

public class ServerStatus {
    private static ServerStatus ourInstance = new ServerStatus();

    public static ServerStatus getInstance() {
        return ourInstance;
    }

    private ServerStatus() {
    }

    private ServerSocket serverSocket;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
