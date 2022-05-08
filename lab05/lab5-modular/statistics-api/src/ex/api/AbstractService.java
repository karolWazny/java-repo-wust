package ex.api;

import ex.api.observer.DefaultEvent;
import ex.api.observer.Listener;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractService implements AnalysisService{
    protected final List<Listener> listeners = new LinkedList<>();

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(){
        for(Listener listener : listeners){
            listener.onEventHappened(new DefaultEvent(this));
        }
    }
}
