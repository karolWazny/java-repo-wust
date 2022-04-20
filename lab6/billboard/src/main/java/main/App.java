package main;

import bilboards.IBillboard;
import bilboards.IManager;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class App implements IBillboard {
    private int id;
    private final int capacity = 5;
    private AtomicBoolean active = new AtomicBoolean(false);
    private final ConcurrentNavigableMap<Integer, Advertisement> adverts = new ConcurrentSkipListMap<>();
    private Duration displayInterval = Duration.ofSeconds(4);

    @Override
    public boolean addAdvertisement(String s, Duration duration, int i) throws RemoteException {
        if(hasNoFreeSlots())
            return false;
        Advertisement ad = new Advertisement();
        ad.duration = duration;
        ad.text = s;
        adverts.put(i, ad);
        return true;
    }

    public boolean hasNoFreeSlots(){
        return adverts.size() >= capacity;
    }

    @Override
    public boolean removeAdvertisement(int i) throws RemoteException {
        Advertisement removedAd = adverts.remove(i);
        return removedAd != null;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[]{adverts.size(), capacity};
    }

    @Override
    public void setDisplayInterval(Duration duration) throws RemoteException {

    }

    @Override
    public boolean start() throws RemoteException {
        synchronized (active){
            if(active.get())
                return false;
            active.set(true);
        }
        Thread thread = new Thread(()->{
            Integer currentKey = Integer.MAX_VALUE;
           while(active.get() && adverts.size() > 0){
               currentKey = adverts.ceilingKey(currentKey);
               if(currentKey == null)
                   currentKey = adverts.firstKey();
               Advertisement currentAd = adverts.get(currentKey);
               Duration timeDifference = currentAd.duration.minus(displayInterval);
               Duration adDuration;
               if(timeDifference.isNegative())
                   adDuration = currentAd.duration;
               else
                   adDuration = displayInterval;
               currentAd.duration = timeDifference;
               try {
                   display(currentAd.text, adDuration);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               if(timeDifference.isNegative())
                   adverts.remove(currentKey);
           }
           active.set(false);
        });
        thread.start();
        return true;
    }

    private void display(String string, Duration displayInterval) throws InterruptedException {
        log.info("Displaying advertisement: " + string);
        log.info("sleeping for " + displayInterval);
        Thread.sleep(displayInterval.toMillis());
    }

    @Override
    public boolean stop() throws RemoteException {
        active.set(false);
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public static void main(String[] args) throws Exception {
        ProgramArgumentsHandler arguments = new ProgramArgumentsHandler(args);
        App app = new App();
        try {
            Registry registry = LocateRegistry.getRegistry(arguments.getHost(), arguments.getPort());
            IManager stub = (IManager) registry.lookup(arguments.getRmiName());
            IBillboard appStub = (IBillboard) UnicastRemoteObject.exportObject(app, arguments.getObjectPort());
            int id = stub.bindBillboard(appStub);
            app.setId(id);
            log.info("Billboard application started.");
            log.info("Billboard id: " + id);
        } catch (Exception e) {
            log.error("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
