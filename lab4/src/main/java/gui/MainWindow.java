package gui;

import lib.DirectoryChooser;
import lib.DirectoryChooserImpl;
import lib.processing.Processor;
import lib.processing.Status;
import lib.processors.SampleProcessor;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindow extends JFrame {
    private DirectoryChooser directoryChooser = new DirectoryChooserImpl();
    private Path currentDirectory;
    private JTextArea processorDescription;
    private Processor processor;

    private JTextField taskField;
    private JTextField statusField;
    private JTextField outputField;

    public MainWindow() {
        super();
        setTitle("Laboratorium 4");

        currentDirectory = Paths.get("")
                .toAbsolutePath();
        System.out.println(currentDirectory);

        JPanel firstPanel = new JPanel();
        JLabel label = new JLabel("Available processors");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstPanel.add(label);
        firstPanel.add(new JList<>(new String[]{"dupa", "cycki", "piwo"}));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(new JButton("Load"));
        buttons.add(new JButton("Refresh"));
        JButton dirButton = new JButton("Directory");
        dirButton.addActionListener(action->currentDirectory = directoryChooser.chooseDirectory(this));
        buttons.add(dirButton);
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
        taskField = new JTextField();
        thirdPanel.add(taskField);
        thirdPanel.add(new JLabel("Chosen processor"));
        processorDescription = new JTextArea();
        processorDescription.setEditable(false);
        thirdPanel.add(processorDescription);
        JButton processButton = new JButton("Process");
        processButton.addActionListener(action->process());
        thirdPanel.add(processButton);
        thirdPanel.add(new JLabel("Status"));
        statusField = new JTextField();
        statusField.setEditable(false);
        thirdPanel.add(statusField);
        thirdPanel.add(new JLabel("Output"));
        outputField = new JTextField();
        outputField.setEditable(false);
        thirdPanel.add(outputField);
        add(thirdPanel);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(680, 160));
    }

    private void process(){
        processor = getProcessor();
        processorDescription.setText(processor.getInfo());

        statusField.setText("0");
        outputField.setText("");
        processor.submitTask(taskField.getText(), this::processorEventCallback);
    }

    private void processorEventCallback(Status status){
        statusField.setText("" + status.getProgress());
        if(status.getProgress() == 100){
            System.out.println(processor.getResult());
            outputField.setText(processor.getResult());
            processor = null;
        }
    }

    private Processor getProcessor(){
        return new SampleProcessor();
    }

    public static void main(String[] args){
        new MainWindow();
    }
}