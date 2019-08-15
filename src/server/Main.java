package server;

import Entity.Host;
import javafx.application.Platform;
import service.GridPaneService;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(GridPaneService gridPaneService) {
        // Singleton property file
        Property prop = Property.getInstance();
        Iterator<Integer> i = prop.PORTS.iterator();

        for (Host server : prop.SERVERS) {
            int sockPort = i.next();
            i.remove();

            Thread serverListener = new Thread(() -> {
                try {
                    Server.start(sockPort, prop.KEY, server);

                    Platform.runLater(() -> gridPaneService.fillHostData(server));

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
