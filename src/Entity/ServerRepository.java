package Entity;

import java.util.ArrayList;
import java.util.List;

public class ServerRepository implements Repository<Server> {

    private static volatile ServerRepository instance;
    List<Server> serverList;

    public ServerRepository() {
        serverList = new ArrayList<>();
    }

    public int getCount(){
        return serverList.size();
    }

    @Override
    public void add(Server item) {
        serverList.add(item);
    }

    @Override
    public void add(Iterable<Server> items) {
        for (Server item : items) {
            add(item);
        }
    }

    @Override
    public Server get(int index) {
        return serverList.get(index);
    }

    @Override
    public void remove(int index) {
        serverList.remove(index);
    }

    @Override
    public void remove(Server item) {
        serverList.remove(item);
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
