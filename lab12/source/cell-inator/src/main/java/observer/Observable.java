package observer;

public interface Observable {
    void addListener(Listener listener);
    void removeListener(Listener listener);
}
