package gui;

import lib.ClassFilesFinder;
import lib.DirectoryChooser;
import lib.DirectoryChooserImpl;
import lib.loader.MyClassLoader;
import lib.processing.Processor;
import lib.processing.Status;
import lib.processors.SampleProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindow extends JFrame {
    private final DirectoryChooser directoryChooser = new DirectoryChooserImpl();
    private final ClassFilesFinder finder = new ClassFilesFinder();
    private Path currentDirectory;
    private final JTextArea processorDescription;
    private Processor processor;

    private final JTextField taskField;
    private final JTextField statusField;
    private final JTextField outputField;

    private final DefaultListModel<Path> availableProcessorsModel = new DefaultListModel<>();

    public MainWindow() {
        super();
        setTitle("Laboratorium 4");

        currentDirectory = Paths.get("")
                .toAbsolutePath();

        JPanel firstPanel = new JPanel();
        JLabel label = new JLabel("Available processors");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstPanel.add(label);
        JList<Path> availableProcessors = new JList<>(availableProcessorsModel);
        availableProcessors.addListSelectionListener(action->{
            loadProcessor(availableProcessors.getSelectedValue());
        });
        availableProcessors.setMinimumSize(new Dimension(200, 300));
        firstPanel.add(new JScrollPane(availableProcessors));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(action->this.refresh());
        buttons.add(refreshButton);
        JButton dirButton = new JButton("Directory");
        dirButton.addActionListener(action->{
            Path path = directoryChooser.chooseDirectory(this);
            currentDirectory = (path != null ? path : currentDirectory);
            refresh();
        });
        buttons.add(dirButton);
        firstPanel.add(buttons);
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.PAGE_AXIS));
        add(firstPanel);

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

        refresh();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(850, 220));
    }

    private void refresh(){
        availableProcessorsModel.removeAllElements();
        finder.classesUnder(this.currentDirectory)
                .stream()
                .map(item-> currentDirectory.relativize(item))
                .forEach(availableProcessorsModel::addElement);
    }

    private void process(){
        statusField.setText("0");
        outputField.setText("");
        processor.submitTask(taskField.getText(), this::processorEventCallback);
    }

    private void loadProcessor(Path path){
        MyClassLoader loader = new MyClassLoader(getClass().getClassLoader());
        loader.setClassRoot(currentDirectory);
        try {
            Class<?> processorClass = loader.loadClass(pathToClassName(path));
            processor = (Processor) processorClass.newInstance();
            displayProcessorInfo();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void displayProcessorInfo(){
        processorDescription.setText(processor.getInfo());
    }

    private String pathToClassName(Path path){
        String output = path.toString();
        output = output.replaceFirst("\\.class$", "");
        output = output.replace(File.separatorChar, '.');
        return output;
    }

    private void processorEventCallback(Status status){
        statusField.setText("" + status.getProgress());
        if(status.getProgress() == 100){
            outputField.setText(processor.getResult());
        }
    }

    public static void main(String[] args){
        new MainWindow();
    }
}