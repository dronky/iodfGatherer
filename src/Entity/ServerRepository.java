package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerRepository implements Repository<Server> {

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
    public void get(int index) {
        serverList.get(index);
    }

    @Override
    public void remove(int index) {
        serverList.remove(index);
    }

    @Override
    public void remove(Server item) {
        serverList.remove(item);
    }
}
