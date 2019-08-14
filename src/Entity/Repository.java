package Entity;

public interface Repository<T> {
    void add(T item);

    void add(Iterable<T> items);

    void get(int index);

    void remove(T item);

    void remove(int index);

    int getCount();
}