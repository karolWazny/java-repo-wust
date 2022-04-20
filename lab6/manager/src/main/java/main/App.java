package main;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class App implements IManager {
    private int nextBillboardId = 0;
    private int nextOrderId = 0;
    private Map<Integer, IBillboard> billboards = new HashMap<>();

    @Override
    public int bindBillboard(IBillboard iBillboard) throws RemoteException {
        billboards.put(nextBillboardId, iBillboard);
        log.info("Bound billboard with id " + nextBillboardId);
        return nextBillboardId++;
    }

    @Override
    public boolean unbindBillboard(int i) throws RemoteException {
        log.info("Unbound billboard with id " + i);
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        log.info("Placed order: " + order.advertText);
        List<IBillboard> availableBillboards = getBillboardsWithFreeSlots();
        if(availableBillboards.isEmpty())
            return false;
        Duration durationPerBillboard = order.displayPeriod.dividedBy(availableBillboards.size());
        availableBillboards.forEach(iBillboard -> {
            try {
                iBillboard.addAdvertisement(order.advertText, durationPerBillboard, nextOrderId);
                iBillboard.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        order.client.setOrderId(nextOrderId++);
        return true;
    }

    private List<IBillboard> getBillboardsWithFreeSlots(){
        return billboards.values()
                .stream()
                .filter(billboard -> {
                    try {
                        int[] capacity = billboard.getCapacity();
                        return capacity[0] < capacity[1];
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean withdrawOrder(int i) throws RemoteException {
        log.info("Withdrawn order with id " + i);
        return false;
    }

    public static void main(String args[]) throws Exception {
        ProgramArgumentsHandler arguments = new ProgramArgumentsHandler(args);

        try {
            App obj = new App();
            IManager stub = (IManager) UnicastRemoteObject.exportObject(obj, arguments.getManagerObjectPort());

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(arguments.getPort());
            registry.bind(arguments.getRmiName(), stub);

            log.info("Server ready");
        } catch (Exception e) {
            log.error("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
