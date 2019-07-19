import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class FtpClient {
    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;
    // constructor


    public FtpClient(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    void open() throws IOException {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);
        ftp.doCommand("site file=jes", "");

        String x = "//TEST     JOB (A00,000),'EXTEND CP',CLASS=Q,       \n" +
            "//             MSGCLASS=P,LINES=150000,BYTES=999999 \n" +
            "//VERIFY   EXEC  PGM=IEBGENER                       \n" +
            "//*                                                 \n" +
            "//SYSPRINT DD DUMMY                                 \n" +
            "//SYSUT2   DD SYSOUT=A                              \n" +
            "//SYSIN    DD DUMMY                                 \n" +
            "//SYSUT1   DD *                                     \n" +
            "    SAMPLE TEST OUTPUT STATEMENT 1                  \n" +
            "//*                                                 ";

        ByteArrayInputStream bais = new ByteArrayInputStream(x.getBytes());

        System.out.println("Start uploading first file");
        ftp.storeUniqueFile("/tmp/1", bais);


    }

    void close() throws IOException {
        ftp.disconnect();
    }
}
