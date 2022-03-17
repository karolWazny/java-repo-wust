package com.lib.logic.observer;

public interface Observable {
    void addListener(Listener listener);
    void removeListener(Listener listener);
}
