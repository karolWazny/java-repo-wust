package lib.processors;

import lib.processing.Processor;
import lib.processing.Status;
import lib.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NonAsciiEscapeProcessor implements Processor {
    private final static String info = "This class escapes any non-ASCII characters in a given string to " +
            "form of \\u0000.";

    private String task;
    private String result;
    private static int taskId=0;

    private void process(){
        StringBuilder b = new StringBuilder();

        for (char c : task.toCharArray()) {
            if (c > 127)
                b.append("\\u").append(String.format("%04X", (int) c));
            else
                b.append(c);
        }
        result = b.toString();
    }

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        this.task = task;
        result = null;
        taskId++;
        AtomicInteger ai = new AtomicInteger(0);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                ()->{
                    ai.incrementAndGet();
                    if(ai.get() < 100){
                        sl.statusChanged(new Status(taskId,ai.get()));
                    } else if(ai.get() == 100){
                        process();
                        sl.statusChanged(new Status(taskId,ai.get()));
                    }
                },
                1, 21, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (ai.get() > 100) {
                    executorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return true;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public String getResult() {
        return result;
    }
}
