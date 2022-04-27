package pl.edu.pwr.java.lab8.gui;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pwr.java.lab8.lib.CSVDataLoader;
import pl.edu.pwr.java.lab8.lib.DataFileChooser;
import pl.edu.pwr.java.lab8.model.entity.*;
import pl.edu.pwr.java.lab8.model.entity.Event;
import pl.edu.pwr.java.lab8.service.EventService;
import pl.edu.pwr.java.lab8.service.InstallmentService;
import pl.edu.pwr.java.lab8.service.PaymentService;
import pl.edu.pwr.java.lab8.service.PersonService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

@Slf4j
public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();
    private final CSVDataLoader dataLoader = new CSVDataLoader();

    private JList<Identifiable> peopleList;
    private DefaultListModel<Identifiable> peopleListModel = new DefaultListModel<>();
    private DefaultListModel<Identifiable> secondListModel = new DefaultListModel<>();

    private JComboBox<String> firstComboBox;
    private JComboBox<String> secondComboBox;

    protected PersonService personService;
    private EventService eventService;
    protected InstallmentService installmentService;
    private PaymentService paymentService;

    private Integer firstComboBoxChoice;
    private Integer secondComboBoxChoice = 0;
    private int firstListPage = 0;
    private int secondListPage = 0;

    public MainWindow() {
        super();
        setTitle("Laboratorium 7");

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
    }

    private void createFirstPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JButton button = new JButton("New person");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action->createPerson());
        panel.add(button);

        JButton newEventButt = new JButton("New event");
        newEventButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        newEventButt.addActionListener(action->createEvent());
        panel.add(newEventButt);

        JButton newInstallmentButt = new JButton("New installment");
        newInstallmentButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        newInstallmentButt.addActionListener(action->createInstallment());
        panel.add(newInstallmentButt);

        JButton paymentButt = new JButton("Payment");
        paymentButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        paymentButt.addActionListener(action->makePayment());
        panel.add(paymentButt);

        add(panel);
    }

    private void createPerson(){
        log.info("Creating new person...");
        Person person = CreatePersonDialogShower.show();
        if(person != null) {
            personService.createPerson(person);
            log.info("Created new person.");
        } else {
            log.info("Creating new person canceled.");
        }
    }

    private void createEvent(){
        log.info("Creating new event...");
        Event event = CreateEventDialog.show();
        if(event != null) {
            eventService.createEvent(event);
            log.info("Created new event.");
        } else {
            log.info("Creating new event canceled.");
        }
    }

    private void createInstallment(){
        log.info("Creating new installment...");
        Installment installment = CreateInstallmentDialog.show(eventService.fetchFutureEvents());
        if(installment != null) {
            installmentService.createInstallment(installment);
            log.info("Created new installment.");
        } else {
            log.info("Creating new installment canceled.");
        }
    }

    private void makePayment(){
        log.info("Creating new payment...");
        Payment payment = CreatePaymentDialog.show(personService.fetchAllPeople(), eventService.fetchAllEvents());
        if(payment != null) {
            paymentService.createPayment(payment);
            log.info("Created new payment.");
        } else {
            log.info("Creating new payment canceled.");
        }
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        firstComboBox = new JComboBox<>(new String[]{
                "People", "Events"
        });

        panel.add(firstComboBox);
        firstComboBox.addActionListener(this::firstComboBoxCallback);

        peopleList = new JList<>(peopleListModel);
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        peopleList.setSelectionModel(selectionModel);
        peopleList.addListSelectionListener(this::firstListSelectionCallback);
        panel.add(new JScrollPane(peopleList));

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        panel.add(buttons);

        add(panel);
    }

    private void firstComboBoxCallback(ActionEvent event){
        Integer newChoice = firstComboBox.getSelectedIndex();
        if(!Objects.equals(firstComboBoxChoice, newChoice)) {
            firstListPage = 0;
            this.firstComboBoxChoice = newChoice;
            if (firstComboBoxChoice == 0) {
                choosePeople();
            } else {
                chooseEvents();
            }
            refreshSecondList();
        }
    }

    private void choosePeople(){
        log.info("Chosen people in first combo box.");
        peopleListModel.removeAllElements();
        peopleListModel.addAll(personService.fetchPeople(firstListPage));
    }

    private void chooseEvents(){
        log.info("Chosen events in first combo box.");
        peopleListModel.removeAllElements();
        peopleListModel.addAll(eventService.fetchEvents(firstListPage));
    }

    private void firstListSelectionCallback(ListSelectionEvent event){
        if(event.getValueIsAdjusting()){
            refreshSecondList();
        }
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        secondComboBox = new JComboBox<>(new String[]{
                "Payments", "Installments"
        });

        secondComboBox.addActionListener(this::secondComboBoxCallback);
        panel.add(secondComboBox);

        JList<Identifiable> list = new JList<>(secondListModel);
        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        panel.add(buttons);

        add(panel);
    }

    private void secondComboBoxCallback(ActionEvent event){
        Integer newChoice = secondComboBox.getSelectedIndex();
        if(!Objects.equals(secondComboBoxChoice, newChoice)) {
            this.secondComboBoxChoice = newChoice;
            refreshSecondList();
        }
    }

    private void refreshSecondList(){
        secondListPage = 0;
        if (secondComboBoxChoice == 0) {
            choosePayments();
        } else {
            chooseInstallments();
        }
    }

    private void choosePayments(){
        log.info("Chosen payments");
        secondListModel.removeAllElements();
        try{
            if(Objects.equals(firstComboBox.getSelectedItem(), "People")){
                secondListModel.addAll(paymentService.fetchPaymentsForPerson(peopleList.getSelectedValue().getId(), secondListPage));
            } else {
                secondListModel.addAll(paymentService.fetchPaymentsForEvent(peopleList.getSelectedValue().getId(), secondListPage));
            }
        } catch (NullPointerException ignored){}
    }

    private void chooseInstallments(){
        log.info("Chosen installments");
        secondListModel.removeAllElements();
        try{
            if(Objects.equals(firstComboBox.getSelectedItem(), "People")){
                secondListModel.addAll(installmentService.fetchUnpaidInstallmentsForPerson(peopleList.getSelectedValue().getId()));
            } else {
                secondListModel.addAll(installmentService.fetchInstallmentsForEvent(peopleList.getSelectedValue().getId(), secondListPage));
            }
        } catch (NullPointerException ignored){}
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setInstallmentService(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}