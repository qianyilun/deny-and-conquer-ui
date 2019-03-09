package facade;

import daemon.ServerWorker;
import model.ConfigurationDTO;
import model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    public static void main(String[] args) throws IOException {
//        launch(1);
    }

    public static void launch(int numOfPlayers, int thickness, int row) throws IOException {
        int requestCounter = 0;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
            List<Socket> socketList = new ArrayList<>();

            while (true) {
                Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.

                // accepting new players
                if (requestCounter < numOfPlayers) {
                    requestCounter++;

                    System.out.println("ServerSocket awaiting connections...");
                    System.out.println("Connection from " + socket + "!");

                    // get the input stream from the connected socket
//                    InputStream inputStream = socket.getInputStream();
//                    // create a DataInputStream so we can read data from it.
//                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//
//                    // read the list of messages from the socket
//
//                    Player player = (Player) objectInputStream.readObject();
                    socketList.add(socket);
//                    System.out.println("Received [" + player.toString() + "] messages from: " + socket);
                }

                // all players are arrived
                if (requestCounter == numOfPlayers) {
                    // trigger: game begin

                    for (int i = 0; i < socketList.size(); i++) {
                        // launch new thread to handle each request
                        Socket playerSocket = socketList.get(i);
                        ServerWorker worker = new ServerWorker("Thread-" + i, playerSocket, socketList, thickness, row);
                        worker.start();
                    }
//                    return;
                }

                if (requestCounter > numOfPlayers) {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing sockets.");
            if (ss != null) {
                ss.close();
            }
        }
    }
}
