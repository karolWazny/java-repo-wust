package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super();
        setTitle("Laboratorium 5");

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

        panel.add(new JLabel("Data:"));
        JTable table = new JTable();
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JButton button = new JButton("Load data");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);
        add(panel);
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Choose function:"));

        JComboBox<String> comboBox = new JComboBox<>();
        panel.add(comboBox);

        JButton button = new JButton("Process");
        panel.add(button);

        add(panel);
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Output:"));
        JTable table = new JTable();
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        add(panel);
    }

    public static void main(String[] args){
        new MainWindow();
    }
}