package pl.edu.pwr.java.lab7.gui;
import pl.edu.pwr.java.lab7.lib.CSVDataLoader;
import pl.edu.pwr.java.lab7.lib.DataFileChooser;
import pl.edu.pwr.java.lab7.lib.DataSet;
import pl.edu.pwr.java.lab7.lib.FileFormatException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Path;

public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();
    private final CSVDataLoader dataLoader = new CSVDataLoader();

    private DataSet inputDataSet;
    private DefaultTableModel inputTableModel = new DefaultTableModel();
    private DefaultTableModel outputTableModel = new DefaultTableModel();

    private JComboBox<String> comboBox;

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

        JButton button = new JButton("Process");
        button.addActionListener(action->processData());
        panel.add(button);

        add(panel);
    }

    private void processData(){

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