package main;

import bilboards.IClient;
import bilboards.IManager;
import bilboards.Order;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

@Slf4j
public class App implements IClient {
    private IManager manager;
    private IClient selfStub;

    public App(String[] args) throws Exception {
        this(new ProgramArgumentsHandler(args));
    }

    public App(ProgramArgumentsHandler arguments) {
        try {
            Registry registry = LocateRegistry.getRegistry(arguments.getHost(), arguments.getPort());
            manager = (IManager) registry.lookup(arguments.getRmiName());
            selfStub = (IClient) UnicastRemoteObject.exportObject(this, arguments.getObjectPort());
            log.info("Application started.");
        } catch (Exception e) {
            log.error("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void setOrderId(int i) throws RemoteException {
        log.info("Order id: " + i);
    }

    private void placeOrder(String text, Duration duration) throws RemoteException {
        Order order = new Order();
        order.advertText = text;
        order.client = selfStub;
        order.displayPeriod = duration;
        log.info("Placing order...");
        boolean success = manager.placeOrder(order);
        if(success)
            log.info("Successfully placed order.");
        else
            log.info("Could not place order.");
    }

    private void withdrawOrder(int id){
        try {
            manager.withdrawOrder(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App(args);
        app.placeOrder("duupa", Duration.ofMinutes(3));
        app.placeOrder("piwo", Duration.ofMinutes(1));
        app.placeOrder("cycki", Duration.ofMinutes(1));
        app.withdrawOrder(0);
    }
}
