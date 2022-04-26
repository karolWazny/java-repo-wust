package pl.edu.pwr.java.lab7.gui;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pwr.java.lab7.lib.CSVDataLoader;
import pl.edu.pwr.java.lab7.lib.DataFileChooser;
import pl.edu.pwr.java.lab7.lib.DataSet;
import pl.edu.pwr.java.lab7.model.entity.Person;
import pl.edu.pwr.java.lab7.service.PersonService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Stack;

@Slf4j
public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();
    private final CSVDataLoader dataLoader = new CSVDataLoader();

    private DataSet inputDataSet;
    private DefaultTableModel inputTableModel = new DefaultTableModel();
    private DefaultTableModel outputTableModel = new DefaultTableModel();

    private JComboBox<String> firstComboBox;
    private JComboBox<String> secondComboBox;

    private PersonService personService;

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
        panel.add(newEventButt);

        JButton newInstallmentButt = new JButton("New installment");
        newInstallmentButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(newInstallmentButt);

        JButton paymentButt = new JButton("Payment");
        paymentButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(paymentButt);

        JButton refreshButt = new JButton("Refresh");
        refreshButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(refreshButt);

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

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JComboBox<>(new String[]{
                "People", "Events"
        }));

        panel.add(new JScrollPane(new JList<>()));

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        panel.add(buttons);

        add(panel);
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JComboBox<>(new String[]{
                "Payments", "Installments"
        }));

        JList<String> list = new JList<>();
        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane);

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        panel.add(buttons);

        add(panel);
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
}