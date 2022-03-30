package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final static String bundleName = "resources.MainWindowBundle";

    private int currentQuestionIndex = 0;

    public MainWindow() {
        super();
        setTitle("Laboratorium 4");

        JPanel firstPanel = new JPanel();
        JLabel label = new JLabel("Available processors");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstPanel.add(label);
        firstPanel.add(new JList<>(new String[]{"dupa", "cycki", "piwo"}));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(new JButton("Load"));
        buttons.add(new JButton("Refresh"));
        buttons.add(new JButton("Directory"));
        firstPanel.add(buttons);
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.PAGE_AXIS));
        add(firstPanel);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.PAGE_AXIS));
        label = new JLabel("Loaded processors");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondPanel.add(label);
        secondPanel.add(new JList<>(new String[]{"beczkowe", "kuflowe", "piwo marki piwo"}));
        JButton unloadButton = new JButton("Unload");
        unloadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondPanel.add(unloadButton);
        add(secondPanel);

        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.PAGE_AXIS));
        thirdPanel.add(new JLabel("Task"));
        thirdPanel.add(new JTextField());
        thirdPanel.add(new JLabel("Chosen processor"));
        thirdPanel.add(new JTextArea());
        thirdPanel.add(new JButton("Process"));
        thirdPanel.add(new JLabel("Status"));
        thirdPanel.add(new JTextField());
        thirdPanel.add(new JLabel("Output"));
        thirdPanel.add(new JTextField());
        add(thirdPanel);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(680, 160));
    }

    public static void main(String[] args){
        new MainWindow();
    }
}