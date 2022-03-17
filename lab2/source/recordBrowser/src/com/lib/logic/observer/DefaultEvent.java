package com.lib.logic.observer;

public class DefaultEvent implements Event {
    private final Observable source;

    public DefaultEvent(Observable source){
        this.source = source;
    }

    @Override
    public Observable getSource() {
        return source;
    }
}
