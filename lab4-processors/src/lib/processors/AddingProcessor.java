package lib.processors;

import lib.processing.Processor;
import lib.processing.Status;
import lib.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddingProcessor implements Processor {
    private final static String info = "Performs adding of some numbers.";

    private String task;
    private String result;
    private static int taskId=0;

    private void process(){
        if(!task.matches("\\d+?\\s*\\+\\s*[\\d]+?(\\s*\\+\\s*\\d+?)*")){
            result = "String is not a valid expression.";
        } else {
            int sum = 0;
            String[] numbers = task.split("\\+");
            for(String str : numbers){
                sum += Integer.parseInt(str.trim());
            }
            result = "" + sum;
        }
    }

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        this.task = task.trim();
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
