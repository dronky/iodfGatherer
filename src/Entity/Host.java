package Entity;

public class Host {
    private String hostname;
    private String login;
    private String password;
    private String iodf;
    private String hardwareIodf;
    private String dateTime;
    private int sockPort;

    public Host(String hostname, String login, String password, int port) {
        this.hostname = hostname;
        this.login = login;
        this.password = password;
        this.sockPort = port;
    }

    public Host() {
        this.hostname = null;
        this.login = null;
        this.password = null;
        this.iodf = null;
        this.hardwareIodf = null;
        this.dateTime = null;
        this.sockPort = 0;
    }

    public int getSockPort() {
        return sockPort;
    }

    public void setSockPort(int sockPort) {
        this.sockPort = sockPort;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIodf() {
        return iodf;
    }

    public void setIodf(String iodf) {
        this.iodf = iodf;
    }

    public String getHardwareIodf() {
        return hardwareIodf;
    }

    public void setHardwareIodf(String hardwareIodf) {
        this.hardwareIodf = hardwareIodf;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
