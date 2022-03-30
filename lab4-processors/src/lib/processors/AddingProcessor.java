package lib.processors;

import lib.processing.Processor;
import lib.processing.StatusListener;

public class AddingProcessor implements Processor {
    @Override
    public boolean submitTask(String task, StatusListener sl) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getResult() {
        return null;
    }
}
