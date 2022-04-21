package main;

import bilboards.IClient;
import bilboards.IManager;
import bilboards.Order;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

@Slf4j
public class App extends JFrame implements IClient {
    private IManager manager;
    private IClient selfStub;
    private JTextArea logTextArea;

    private Integer latestOrderId;

    public App(String[] args) throws Exception {
        this(new ProgramArgumentsHandler(args));
    }

    public App(ProgramArgumentsHandler arguments) {
        super();
        setTitle("Laboratorium 6");

        try {
            Registry registry = LocateRegistry.getRegistry(arguments.getHost(), arguments.getPort());
            manager = (IManager) registry.lookup(arguments.getRmiName());
            selfStub = (IClient) UnicastRemoteObject.exportObject(this, arguments.getObjectPort());
        } catch (Exception e) {
            log.error("Client exception: " + e.toString());
            e.printStackTrace();
        }

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
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setSize(new Dimension(200, 100));
        panel.add(new JScrollPane(logTextArea));
        add(panel);
    }

    private void createSecondPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Order id:"));
        JTextField orderIdTextField = new JTextField();
        panel.add(orderIdTextField);
        JButton button = new JButton("Withdraw");
        button.addActionListener(action->{
            Integer id = Integer.parseInt(orderIdTextField.getText().trim());
            withdrawOrder(id);
        });
        panel.add(button);

        add(panel);
    }

    private void createFirstPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Ad text:"));
        JTextField advertTextField = new JTextField();
        panel.add(advertTextField);
        panel.add(new JLabel("Duration in minutes:"));
        JTextField durationTextField = new JTextField();
        panel.add(durationTextField);
        JButton placeOrderButton = new JButton("Place order");
        placeOrderButton.addActionListener(action->{
            Integer numberOfMinutes = Integer.parseInt(durationTextField.getText().trim());
            try {
                placeOrder(advertTextField.getText(), Duration.ofMinutes(numberOfMinutes));
                advertTextField.setText("");
                durationTextField.setText("");
            } catch (RemoteException e) {
                log.error(e.toString());
            }
        });
        panel.add(placeOrderButton);

        add(panel);
    }

    @Override
    public void setOrderId(int i) throws RemoteException {
        latestOrderId = i;
        log.info("Order id: " + i);
    }

    private void placeOrder(String text, Duration duration) throws RemoteException {
        Order order = new Order();
        order.advertText = text;
        order.client = selfStub;
        order.displayPeriod = duration;
        log.info("Placing order...");
        boolean success = manager.placeOrder(order);
        if(success){
            log.info("Successfully placed order with id " + latestOrderId);
            logTextArea.append("ORDER " + latestOrderId + ": " + text + "\n");
        }
        else{
            log.info("Could not place order.");
            logTextArea.append("All billboards are busy!");
        }
        latestOrderId = null;
    }

    private void withdrawOrder(int id){
        try {
            boolean success = manager.withdrawOrder(id);
            if(success)
                logTextArea.append("WITHDRAWN: " + id + "\n");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        if(System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        RMISocketFactory.setSocketFactory(new SafeSocketFactory());
        App app = new App(args);
    }
}
