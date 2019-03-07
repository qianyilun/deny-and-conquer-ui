package business;

import model.Host;
import model.Player;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginService {
    /**
     * register the new client to server with player's information
     * with TCP socket
     *
     * @return
     */
    public boolean registerToServer(String name, Color color) throws IOException, ClassNotFoundException {
        InetAddress thisMachineIP = InetAddress.getLocalHost();
        Player player = new Player(name, thisMachineIP, color);

        // TODO: move to independent class
        InetAddress serverIp = InetAddress.getByName("192.168.0.13");


        Socket clientSocket = new Socket("192.168.0.13", 7777);
        System.out.println("Connected!");


        // get the output stream from the socket.
        OutputStream outputStream = clientSocket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(player);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        Host host = (Host) objectInputStream.readObject();

        System.out.println(host);

        return true;
    }
}
