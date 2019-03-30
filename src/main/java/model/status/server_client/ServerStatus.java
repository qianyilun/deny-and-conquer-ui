package model.status.server_client;

import daemon.ServerWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

    private List<ServerWorker> workerList;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<ServerWorker> workerList) {
        this.workerList = workerList;
    }
}
