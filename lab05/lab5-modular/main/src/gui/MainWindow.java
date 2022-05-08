package gui;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;
import ex.api.observer.Event;
import lib.AnalysisServiceLoader;
import lib.CSVDataLoader;
import lib.DataFileChooser;
import lib.FileFormatException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();
    private final CSVDataLoader dataLoader = new CSVDataLoader();

    private DataSet inputDataSet;
    private DataSet result;
    private DefaultTableModel inputTableModel = new DefaultTableModel();
    private DefaultTableModel outputTableModel = new DefaultTableModel();
    private List<AnalysisService> services;

    private AnalysisService currentService;

    private JComboBox<String> comboBox;

    public MainWindow() {
        super();
        setTitle("Laboratorium 5");

        loadServices();

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

    private void loadServices(){
        services = AnalysisServiceLoader.loadAvailableServices();
        for(AnalysisService service : services){
            service.addListener(this::finishedProcessingEventCallback);
        }
    }

    private void finishedProcessingEventCallback(Event event){
        System.out.println("dupa");
        if(event.getSource() != currentService)
            return;
        try {
            result = currentService.retrieve(true);
            outputTableModel.setDataVector(result.getData(), result.getHeader());
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
    }

    private void createFirstPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Data:"));
        JTable table = new JTable(inputTableModel);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JButton button = new JButton("Load data");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action->loadData());
        panel.add(button);
        add(panel);
    }

    private void loadData(){
        Path pathToData = fileChooser.chooseFile(this);
        if(pathToData != null){
            try{
                inputDataSet = dataLoader.readFromFile(pathToData);
                inputTableModel.setDataVector(inputDataSet.getData(), inputDataSet.getHeader());
                outputTableModel.setDataVector(new String[0][], new String[0]);
            } catch (FileFormatException e){
                throw new RuntimeException("File " + pathToData + " is not a proper CSV file.");
            }
        }
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Choose function:"));

        comboBox = new JComboBox<>(services.stream()
                .map(AnalysisService::getName)
                .collect(Collectors.toList())
                .toArray(new String[0]));
        panel.add(comboBox);

        JButton button = new JButton("Process");
        button.addActionListener(action->processData());
        panel.add(button);

        add(panel);
    }

    private void processData(){
        currentService = services.get(comboBox.getSelectedIndex());
        try {
            currentService.submit(inputDataSet);
        } catch (AnalysisException e) {
            throw new RuntimeException("Cannot submit new data while service is still processing previous request!");
        }
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Output:"));
        JTable table = new JTable(outputTableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        add(panel);
    }

    public static void main(String[] args){
        new MainWindow();
    }
}