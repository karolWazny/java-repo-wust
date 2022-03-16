package lib.gui;

import app.BottomPanel;
import lib.logic.DirectoryChooser;
import lib.logic.DirectoryChooserImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class MainWindow extends JFrame {
    private DirectoryChooser dirChooser = new DirectoryChooserImpl();

    private final JTextField directoryTextField;
    private JButton browseButton;
    private JTextField metaField;

    private Path directory;

    public void setDirectory(Path directory) {
        this.directory = directory;
        directoryTextField.setText(directory.toString());
    }

    public MainWindow() {
        super("Snapshooter - windowed");

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
        topPanel.setPreferredSize(new Dimension(900, 30));

        add(topPanel);

        metaField = new JTextField();
        metaField.setMaximumSize(new Dimension(1000, 30));
        metaField.setEditable(false);

        add(metaField);

        JPanel bottomPanel = new BottomPanel();

        JScrollPane scrollPane = new JScrollPane(bottomPanel);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(700, 230));
    }

    private static JTextArea uneditableArea(){
        JTextArea output = new JTextArea();
        output.setEditable(false);
        return output;
    }

    public static void main(String[] args){
        JFrame frame = new MainWindow();
    }
}
