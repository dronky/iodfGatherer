package server;

import Entity.Host;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Property {

    static public File FILE = null;
    static public String ADDRESS = null;
    static public String CONSOLE = null;
    static public String MSGCLASS = null;
    static public Set<Integer> PORTS = null;
    static public String[] SYSTEMS = null;
    static public List<Host> SERVERS = null;
    private boolean propertySaved;
    Properties property = null;

    private static volatile Property instance;

    private Property() throws IOException {

        property = new Properties();
        reReadFile();
        propertySaved = true;
    }

    public boolean isPropertySaved() {
        return propertySaved;
    }

    public void setPropertySaved(boolean propertySaved) {
        this.propertySaved = propertySaved;
    }

    public void reReadFile() {
        try {
            FILE = new File("server.properties");
            FileInputStream fileInputStream = new FileInputStream(FILE);
            property.load(fileInputStream);

            SYSTEMS = property.getProperty("SYSTEMS").split(";");
            if (property.stringPropertyNames().contains("PORTS")) {
                PORTS = Arrays.stream(property.getProperty("PORTS").split(";")).map(Integer::parseInt).collect(Collectors.toSet());
                // PORTS COUNT MUST BE EQUAL TO SYSTEMS OTHERWISE USE DEFAULT PORT SET
                if (PORTS.size() != SYSTEMS.length) setDefaultPorts();
            } else {
                setDefaultPorts();
            }

            // CONVERT TO LIST
            SERVERS = new ArrayList<>();
            parseHostsToList(SYSTEMS, PORTS);

            ADDRESS = property.getProperty("ADDRESS");
            CONSOLE = property.getProperty("CONSOLE");
            MSGCLASS = property.getProperty("MSGCLASS");
        } catch (IOException e) {
            System.err.println("Error occured while was reading properties file");
        }
    }

    public void updateSystems(String systems) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(FILE);
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        property.load(fileInputStream);
        property.setProperty("SYSTEMS", systems);
        property.save(fileOutputStream,"");
        propertySaved = true;
    }

    public void updateAddress(String address) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(FILE);
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        property.load(fileInputStream);
        property.setProperty("ADDRESS", address);
        property.save(fileOutputStream,"");
        propertySaved = true;
    }

    public void updateConsole(String console) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(FILE);
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        property.load(fileInputStream);
        property.setProperty("CONSOLE", console);
        property.save(fileOutputStream,"");
        propertySaved = true;
    }

    public void updateMsgclass(String msgclass) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(FILE);
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        property.load(fileInputStream);
        property.setProperty("MSGCLASS", msgclass);
        property.save(fileOutputStream,"");
        propertySaved = true;
    }


    private void setDefaultPorts() {
        PORTS = IntStream.rangeClosed(3344, 3444).boxed().collect(Collectors.toSet());
    }

    public static void setServers(List<Host> servers) {
        Property.SERVERS = servers;
    }

    private void parseHostsToList(String[] systems, Set<Integer> ports) {
        for (String system : systems) {
            String[] credentials = system.split(" ");
            String host = credentials[0];
            String login = credentials[1];
            String password = credentials[2];
            Iterator<Integer> i = ports.iterator();
            // TODO add check:
            int port = i.next();

            SERVERS.add(new Host(host, login, password, port));
        }
    }

    public static Property getInstance() {
        // Вам может быть неясно зачем мы используем дублирующую локальную
        // переменную здесь. Это — микрооптимизация.
        //
        // Поле одиночки объявлено как volatile, что заставляет программу
        // обновлять её значение из памяти каждый раз при доступе к переменной,
        // тогда как значение обычной переменной может быть записано в регистр
        // процессора для более быстрого чтения. Используя дополнительную
        // локальную перменную, мы можем ускорить работу с переменной, обновляя
        // значение поля только тогда, когда действительно нужно.
        Property result = instance;
        if (result == null) {
            synchronized (Property.class) {
                result = instance;
                if (result == null) {
                    try {
                        instance = result = new Property();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }
}


