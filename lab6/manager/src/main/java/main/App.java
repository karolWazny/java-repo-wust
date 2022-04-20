package main;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import lombok.extern.log4j.Log4j2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Log4j2
public class App implements IManager {
    public static void main(String args[]) throws Exception {
        ProgramArgumentsHandler arguments = new ProgramArgumentsHandler(args);

        try {
            App obj = new App();
            IManager stub = (IManager) UnicastRemoteObject.exportObject(obj, arguments.getManagerObjectPort());

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(arguments.getPort());
            registry.bind(arguments.getRmiName(), stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int bindBillboard(IBillboard iBillboard) throws RemoteException {
        log.info("Bound billboard with id " + 0);
        return 0;
    }

    @Override
    public boolean unbindBillboard(int i) throws RemoteException {
        log.info("Unbound billboard with id " + i);
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        log.info("Placed order: " + order.advertText);
        return false;
    }

    @Override
    public boolean withdrawOrder(int i) throws RemoteException {
        log.info("Withdrawn order with id " + i);
        return false;
    }
}
