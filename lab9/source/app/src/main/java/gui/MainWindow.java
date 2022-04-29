package gui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();

    private JComboBox<String> firstComboBox;
    private JComboBox<String> secondComboBox;

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

        add(panel);
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        firstComboBox = new JComboBox<>(new String[]{
                "People", "Events"
        });

        panel.add(firstComboBox);

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

        secondComboBox = new JComboBox<>(new String[]{
                "Payments", "Installments"
        });

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        panel.add(buttons);

        add(panel);
    }
}