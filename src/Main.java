import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        Thread serverListener = new Thread(() -> {
            try {
                Server.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        serverListener.start();

        Thread ftpThread = new Thread(() -> {
            FtpClient ftpClient = new FtpClient("ts01", 21, "a09", "a09");
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
