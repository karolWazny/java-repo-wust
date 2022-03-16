package lib.gui;

import lib.logic.ApplicationModel;
import lib.logic.DirectoryChooser;
import lib.logic.DirectoryChooserImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class MainWindow extends JFrame {
    private DirectoryChooser dirChooser = new DirectoryChooserImpl();
    private ApplicationModel model;

    private final JTextField directoryTextField;
    private JButton browseButton;
    private JTextField metaField;
    private BottomPanel bottomPanel;

    public void setDirectory(Path directory) {
        model.setDirectory(directory);
        directoryTextField.setText(directory.toString());
        bottomPanel.setRecords(model.recordNames());
    }

    private static JTextArea uneditableArea(){
        JTextArea output = new JTextArea();
        output.setEditable(false);
        return output;
    }

    public MainWindow(ApplicationModel model) throws HeadlessException {
        super("Records viewer - windowed");
        this.model = model;

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        JLabel lab1 = new JLabel("Directory: ");
        topPanel.add(lab1);
        directoryTextField = new JTextField();
        directoryTextField.setText("No directory chosen.");
        directoryTextField.setColumns(50);
        directoryTextField.setEnabled(true);
        directoryTextField.setEditable(false);
        directoryTextField.setMaximumSize(new Dimension(1000, 30));
        topPanel.add(directoryTextField);
        browseButton = new JButton("Browse");
        browseButton.addActionListener((ActionEvent action)->{
            Path dirChosen = dirChooser.chooseDirectory(this);
            if(dirChosen == null)
                return;
            setDirectory(dirChosen);
        });
        topPanel.add(browseButton);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.setPreferredSize(new Dimension(700, 30));

        add(topPanel);

        metaField = new JTextField();
        metaField.setMaximumSize(new Dimension(1000, 30));
        metaField.setEditable(false);

        add(metaField);

        bottomPanel = new BottomPanel(model);
        add(bottomPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(700, 230));
    }

    public static void main(String[] args){
        JFrame frame = new MainWindow(new ApplicationModel());
    }
}
