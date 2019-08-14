package Entity;

import java.util.ArrayList;
import java.util.List;

public class ServerRepository implements Repository<Host> {

    private static volatile ServerRepository instance;
    List<Host> hostList;

    public ServerRepository() {
        hostList = new ArrayList<>();
    }

    public int getCount(){
        return hostList.size();
    }

    @Override
    public void add(Host item) {
        hostList.add(item);
    }

    @Override
    public void add(Iterable<Host> items) {
        for (Host item : items) {
            add(item);
        }
    }

    @Override
    public Host get(int index) {
        return hostList.get(index);
    }

    @Override
    public void remove(int index) {
        hostList.remove(index);
    }

    @Override
    public void remove(Host item) {
        hostList.remove(item);
    }

    public static ServerRepository getInstance() {
        ServerRepository result = instance;
        if (result == null) {
            synchronized (ServerRepository.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ServerRepository();
                }
            }
        }
        return instance;
    }

}
