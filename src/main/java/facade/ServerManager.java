package facade;

import daemon.ServerWorker;
import model.status.game.LocalStatus;
import model.dto.PlayerDTO;
import model.status.server_client.ServerStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    public static boolean launch(int numOfPlayers, int thickness, int row, int percent, PlayerDTO hostPlayerDTO) throws IOException, ClassNotFoundException {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
            ServerStatus.getInstance().setServerSocket(ss);
            List<PlayerDTO> playerDTOs = new ArrayList<>();
            List<Socket> clientSocketList = new ArrayList<>();
            List<ServerWorker> threadWorkerList = new ArrayList<>();

            // add host player's info
            playerDTOs.add(hostPlayerDTO);

            // waiting for incoming players
            for (int i = 0; i < numOfPlayers; i++) {
                Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
                PlayerDTO playerDTO = parsePlayerDTOFromSocket(socket, i);
                playerDTO.setPlayerId(i+1); // host player will be with id = 0
                playerDTOs.add(playerDTO);
                clientSocketList.add(socket);
            }

            // handle socket communication between server and all clients
            for (int i = 0; i < numOfPlayers; i++) {
                Socket socket = clientSocketList.get(i);
                ServerWorker worker = new ServerWorker(i, socket, playerDTOs, thickness, row, percent);
                threadWorkerList.add(worker);
                worker.start();
            }

            ServerStatus.getInstance().setWorkerList(threadWorkerList);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
//                ss.close();
            }
        }
        return false;
    }

    private static PlayerDTO parsePlayerDTOFromSocket(Socket socket, int id) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        PlayerDTO playerDTO = (PlayerDTO) objectInputStream.readObject();
        playerDTO.setPlayerId(id);
        return playerDTO;
    }
}
