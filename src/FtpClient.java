import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        String client = new String(Files.readAllBytes(Paths.get("SOCKCLI")));

        String x =
            "//A09TST  JOB A09,MAKARENKO,NOTIFY=&SYSUID,MSGCLASS=Z\n" +
            "//STEP2     EXEC  PGM=IEBGENER                       \n" +
            "//SYSIN     DD  DUMMY                                \n" +
            "//SYSPRINT  DD  SYSOUT=*                             \n" +
            "//SYSUT2    DD  DSN=&&ABC(AA),DISP=(,PASS),          \n" +
            "//        SPACE=(CYL,(20,,2)),DCB=(RECFM=FB)         \n" +
            "//SYSUT1    DD  *                                    \n" +
            client                                            +  "\n" +
            "//RUNREXX  EXEC PGM=IKJEFT01,PARM='AA'               \n" +
            "//SYSEXEC   DD DSN=&&ABC,DISP=(SHR,DELETE)           \n" +
            "//SYSTSPRT  DD SYSOUT=*                              \n" +
            "//SYSTSIN   DD *                                     \n" +
            "/*                                                   \n" +
            "//                                                   ";

        ByteArrayInputStream bais = new ByteArrayInputStream(x.getBytes());

        System.out.println("Start uploading first file");
        ftp.storeUniqueFile("/tmp/1", bais);


    }

    void close() throws IOException {
        ftp.disconnect();
    }
}
