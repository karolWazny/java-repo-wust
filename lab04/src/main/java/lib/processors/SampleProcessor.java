package lib.processors;

import lib.processing.Processor;
import lib.processing.Status;
import lib.processing.StatusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SampleProcessor implements Processor {
    private static int taskId=0;
    private String result = null;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        taskId++;
        AtomicInteger ai = new AtomicInteger(0);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                ()->{
                    System.out.println("running"); // do debbugowania
                    ai.incrementAndGet();
                    if(ai.get() < 100){
                        sl.statusChanged(new Status(taskId,ai.get()));
                    } else if(ai.get() == 100){
                        result = task.toUpperCase();
                        sl.statusChanged(new Status(taskId,ai.get()));
                    }
                },
                1, 10, TimeUnit.MILLISECONDS);

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
                    System.out.println("finished");
                    //scheduleFuture.cancel(true);
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
        return "Zamiana literek na duże";
    }

    @Override
    public String getResult() {
        return result;
    }
}
