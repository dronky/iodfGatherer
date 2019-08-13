import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Singleton
        Property prop = Property.getInstance();
        Set<Integer> portlist = new HashSet<>(prop.PORTS);
        Iterator<Integer> i = portlist.iterator();

        for (String system : prop.SYSTEMS) {
            String[] credentials = system.split(":");
            String host = credentials[0];
            String login = credentials[1];
            String password = credentials[2];
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
                FtpClient ftpClient = new FtpClient(host, 21, login, password, prop.KEY, sockPort, prop.ADDRESS, prop.CONSOLE, prop.MSGCLASS);
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
