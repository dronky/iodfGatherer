package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public final class Property {

    static public String[] SYSTEMS = null;
    static public String KEY = null;
    static public String ADDRESS = null;
    static public String CONSOLE = null;
    static public String MSGCLASS = null;
    static public Set<Integer> PORTS = null;

    private static volatile Property instance;

    private Property() {

        Properties property = new Properties();
        try {
            FileInputStream fis_properties = new FileInputStream("server.properties");
            property.load(fis_properties);
            SYSTEMS = property.getProperty("SYSTEMS").split(";");
            PORTS = Arrays.stream(property.getProperty("PORTS").split(";")).map(Integer::parseInt).collect(Collectors.toSet());
            KEY = property.getProperty("KEY");
            ADDRESS = property.getProperty("ADDRESS");
            CONSOLE = property.getProperty("CONSOLE");
            MSGCLASS = property.getProperty("MSGCLASS");
        } catch (IOException e) {
            System.err.println("Error occured while was reading properties file");
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


