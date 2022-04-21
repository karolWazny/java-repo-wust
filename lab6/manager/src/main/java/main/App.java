package main;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class App extends JFrame implements IManager {
    private int nextBillboardId = 0;
    private int nextOrderId = 0;
    private Map<Integer, IBillboard> billboards = new HashMap<>();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private Map<Integer, BillboardMeta> billboardsMeta = new HashMap<>();

    public App(){
        super();
        setTitle("Laboratorium 6 - manager");
        createFirstPanel();
        createSecondPanel();
        createThirdPanel();

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(850, 220));

        log.info("Application started.");
    }

    private void createThirdPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        add(panel);
    }

    private void createSecondPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Swap interval"));
        JTextField swapTextField = new JTextField();
        swapTextField.setMaximumSize(new Dimension(100, 30));
        panel.add(swapTextField);
        JButton setSwapInterval = new JButton("Set interval");
        panel.add(setSwapInterval);
        JButton stopButton = new JButton("Stop");
        panel.add(stopButton);
        JButton startButton = new JButton("Start");
        panel.add(startButton);

        add(panel);
    }

    private void createFirstPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        tableModel.setColumnIdentifiers(obtainHeaders());
        JButton button = new JButton("Refresh");
        panel.add(button);
        add(panel);
    }

    private String[] obtainHeaders(){
        return new String[]{
          "ID",
                "Swap interval",
          "Active",
          "Used slots",
          "Total capacity"
        };
    }

    private void refreshTable(){
        tableModel.setDataVector(obtainBillboardsData(), obtainHeaders());
    }

    private String[][] obtainBillboardsData(){
        String[][] output = new String[0][];
        output = billboardsMeta.values().stream()
                .map(meta->{
                    try {
                        int[] capacity = meta.billboard.getCapacity();
                        return new String[]{
                                "" + meta.id,
                                "" + meta.swapInterval,
                                "" + (meta.active && capacity[0] > 0),
                                "" + capacity[0],
                                "" + capacity[1]
                        };
                    } catch (RemoteException e) {
                        log.error(e.toString());
                        return null;
                    }

                }).collect(Collectors.toList())
                .toArray(output);
        return output;
    }

    @Override
    public int bindBillboard(IBillboard iBillboard) throws RemoteException {
        billboards.put(nextBillboardId, iBillboard);
        BillboardMeta meta = new BillboardMeta();
        iBillboard.setDisplayInterval(Duration.ofSeconds(4));
        meta.billboard = iBillboard;
        meta.active = false;
        meta.id = nextBillboardId;
        meta.swapInterval = 4;
        billboardsMeta.put(nextBillboardId, meta);
        refreshTable();
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
        refreshTable();
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
        billboards.values()
                        .stream()
                                .forEach(iBillboard -> {
                                    try {
                                        iBillboard.removeAdvertisement(i);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                });
        log.info("Withdrawn order with id " + i);
        refreshTable();
        return true;
    }

    public static void main(String args[]) throws Exception {
        ProgramArgumentsHandler arguments = new ProgramArgumentsHandler(args);
        if(System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        RMISocketFactory.setSocketFactory(new SafeSocketFactory());
        try {
            App obj = new App();
            IManager stub = (IManager) UnicastRemoteObject.exportObject(obj, 0);

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
