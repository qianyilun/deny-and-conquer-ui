package facade;

import daemon.ServerWorker;
import model.PlayerDTO;

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
            List<PlayerDTO> playerDTOs = new ArrayList<>();
            List<Socket> sockets = new ArrayList<>();

            playerDTOs.add(hostPlayerDTO);

            for (int i = 0; i < numOfPlayers; i++) {
                Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
                PlayerDTO playerDTO = parsePlayerDTOFromSocket(socket, i);
                playerDTOs.add(playerDTO);
                sockets.add(socket);
            }

            for (int i = 0; i < numOfPlayers; i++) {
                Socket socket = sockets.get(i);
                ServerWorker worker = new ServerWorker(i, socket, playerDTOs, thickness, row, percent);
                worker.start();
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

    private static PlayerDTO parsePlayerDTOFromSocket(Socket socket, int id) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        PlayerDTO playerDTO = (PlayerDTO) objectInputStream.readObject();
        playerDTO.setPlayerId(id);
        return playerDTO;
    }
}
