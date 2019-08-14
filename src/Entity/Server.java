package Entity;

public class Server {
    private String hostname;
    private String login;
    private String password;

    public Server(String hostname, String login, String password) {
        this.hostname = hostname;
        this.login = login;
        this.password = password;
    }

    public Server() {
        this.hostname = null;
        this.login = null;
        this.password = null;
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
}
