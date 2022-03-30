package lib.processors;

import lib.processing.Processor;
import lib.processing.StatusListener;

public class AsciiToUnicodeProcessor implements Processor {
    private final static String info = "Transforms \\u0000 encoded non-ASCII characters to normal characters.";

    private String task;
    private String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        return false;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getResult() {
        return null;
    }
}
