package server;

import Entity.Host;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main() {
        // Singleton
        Property prop = Property.getInstance();
        Set<Integer> portlist = new HashSet<>(prop.PORTS);
        Iterator<Integer> i = portlist.iterator();

        for (Host server : prop.SERVERS) {
            int sockPort = i.next();
            i.remove();

            Thread serverListener = new Thread(() -> {
                try {
                    Server.start(sockPort, prop.KEY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            serverListener.start();

            Thread ftpThread = new Thread(() -> {
                FtpClient ftpClient = new FtpClient(server.getHostname(), 21, server.getLogin(), server.getPassword(), prop.KEY, sockPort, prop.ADDRESS, prop.CONSOLE, prop.MSGCLASS);
                try {
                    ftpClient.open();
                    ftpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            ftpThread.start();
        }
    }
}
