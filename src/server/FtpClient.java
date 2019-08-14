package server;

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

    private String key;
    private int sockPort;
    private String address;
    private String console;
    private String msgclass;

    // constructor


    public FtpClient(String server, int port, String user, String password, String key, int sockPort, String address, String console, String msgclass) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
        this.key = key;
        this.sockPort = sockPort;
        this.address = address;
        this.console = console;
        this.msgclass = msgclass;
    }

    void open() throws IOException {
        ftp = new FTPClient();

        //DEBUG
        //        long threadId = Thread.currentThread().getId();
        //        System.out.println(threadId+":FTP CLIENT started with sock port " + sockPort);
        //        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP server.server");
        }

        ftp.login(user, password);
        ftp.doCommand("site file=jes", "");

        String client = new String(Files.readAllBytes(Paths.get("SOCKCLI")));
        StringBuffer stringBuffer = new StringBuffer(client);

        replace(stringBuffer, "#KEY#", key);
        replace(stringBuffer, "#PORT#", String.valueOf(sockPort));
        replace(stringBuffer, "#ADDR#", address);
        replace(stringBuffer, "#CONSNAME#", console);

        String x =
            "//IODFGATH  JOB DEV,IODFGATH,MSGCLASS=" + msgclass + "\n" +
                "//STEP2     EXEC  PGM=IEBGENER                       \n" +
                "//SYSIN     DD  DUMMY                                \n" +
                "//SYSPRINT  DD  SYSOUT=*                             \n" +
                "//SYSUT2    DD  DSN=&&ABC(AA),DISP=(,PASS),          \n" +
                "//        SPACE=(CYL,(20,,2)),DCB=(RECFM=FB)         \n" +
                "//SYSUT1    DD  *                                    \n" +
                stringBuffer.toString() + "\n" +
                "//RUNREXX  EXEC PGM=IKJEFT01,PARM='AA'               \n" +
                "//SYSEXEC   DD DSN=&&ABC,DISP=(SHR,DELETE)           \n" +
                "//SYSTSPRT  DD SYSOUT=*                              \n" +
                "//SYSTSIN   DD *                                     \n" +
                "/*                                                   \n" +
                "//                                                   ";

        ByteArrayInputStream bais = new ByteArrayInputStream(x.getBytes());

//        System.out.println("Start uploading first file");
        ftp.storeUniqueFile("/tmp/" + sockPort, bais);
    }

    void replace(StringBuffer sb, String oldValue, String newValue) {
        int index = sb.indexOf(oldValue);
        sb.replace(index, index + oldValue.length(), newValue);
    }

    void close() throws IOException {
        ftp.disconnect();
    }
}
