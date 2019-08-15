package server;

import Entity.Host;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Property {

    static public String KEY = null;
    static public String ADDRESS = null;
    static public String CONSOLE = null;
    static public String MSGCLASS = null;
    static public Set<Integer> PORTS = null;
    static public List<Host> SERVERS = null;
    private int[] sockPorts = null;

    private static volatile Property instance;

    private Property() {
        //fill default socket ports
        sockPorts = IntStream.rangeClosed(3344, 3444).toArray();

        Properties property = new Properties();
        try {
            FileInputStream fis_properties = new FileInputStream("server.properties");
            property.load(fis_properties);

            String[] SYSTEMS = property.getProperty("SYSTEMS").split(";");
            if (property.stringPropertyNames().contains("PORTS")) {
                PORTS = Arrays.stream(property.getProperty("PORTS").split(";")).map(Integer::parseInt).collect(Collectors.toSet());
                // PORTS MUST BE EQUAL TO SYSTEMS OTHERWISE USE DEFAULT PORT SET
                if (PORTS.size() != SYSTEMS.length) setDefaultPorts();
            } else {
                setDefaultPorts();
            }

            // CONVERT TO LIST
            SERVERS = new ArrayList<>();
            parseHostsToList(SYSTEMS, PORTS);

            KEY = property.getProperty("KEY");
            ADDRESS = property.getProperty("ADDRESS");
            CONSOLE = property.getProperty("CONSOLE");
            MSGCLASS = property.getProperty("MSGCLASS");
        } catch (IOException e) {
            System.err.println("Error occured while was reading properties file");
        }
    }

    private void setDefaultPorts() {
        PORTS = IntStream.rangeClosed(3344, 3444).boxed().collect(Collectors.toSet());
    }

    public static void setServers(List<Host> servers) {
        Property.SERVERS = servers;
    }

    private void parseHostsToList(String[] systems, Set<Integer> ports) {
        for (String system : systems) {
            String[] credentials = system.split(":");
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
                    instance = result = new Property();
                }
            }
        }
        return instance;
    }
}


