package facade;

import daemon.ServerWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    public static void main(String[] args) throws IOException {
//        launch(1);
    }

    public static boolean launch(int numOfPlayers, int thickness, int row, int percent) throws IOException {
        int requestCounter = 0;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
            List<Socket> socketList = new ArrayList<>();

            for (int i = 0; i < numOfPlayers; i++) {
                Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.

                // accepting new players
                if (requestCounter < numOfPlayers) {
                    requestCounter++;

                    System.out.println("ServerSocket awaiting connections...");
                    System.out.println("Connection from " + socket + "!");

                    socketList.add(socket);
                }

            }

            for (int i = 0; i < socketList.size(); i++) {
                // launch new thread to handle each request
                Socket playerSocket = socketList.get(i);
                ServerWorker worker = new ServerWorker(i, playerSocket, socketList, thickness, row, percent);
                worker.start();
            }

            for (int i = 0; i < socketList.size(); i++) {
                socketList.get(i).close();
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing sockets.");
            if (ss != null) {
                ss.close();
            }
        }
        return false;
    }
}
