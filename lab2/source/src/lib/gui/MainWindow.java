package lib.gui;

import lib.logic.ApplicationModel;
import lib.logic.DirectoryChooser;
import lib.logic.DirectoryChooserImpl;
import lib.logic.observer.Event;
import lib.logic.observer.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class MainWindow extends JFrame implements Listener {
    private final DirectoryChooser dirChooser = new DirectoryChooserImpl();
    private final ApplicationModel model;

    private final JTextField directoryTextField;
    private final JTextField metaField;
    private final BottomPanel bottomPanel;

    public void setDirectory(Path directory) {
        model.setDirectory(directory);
        directoryTextField.setText(directory.toString());
        bottomPanel.setRecords(model.recordNames());
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
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener((ActionEvent action)->{
            Path dirChosen = dirChooser.chooseDirectory(this);
            if(dirChosen == null)
                return;
            setDirectory(dirChosen);
        });
        topPanel.add(browseButton);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));

        add(topPanel);

        metaField = new JTextField();
        metaField.setMaximumSize(new Dimension(1000, 30));
        metaField.setEditable(false);

        add(metaField);

        bottomPanel = new BottomPanel(model);
        add(bottomPanel);
        bottomPanel.addListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        //setMinimumSize(new Dimension(600, 310));
        setPreferredSize(new Dimension(600, 310));
    }

    public static void main(String[] args){
        JFrame frame = new MainWindow(new ApplicationModel());
    }

    @Override
    public void onEventHappened(Event event) {
        Dimension dim = getSize();
        setSize(new Dimension(bottomPanel.getWidth(), dim.height));
        if(model.wasLastRecordInCache())
            metaField.setText("This record was loaded from RAM.");
        else
            metaField.setText("This record was loaded from hard drive.");
        //pack();
    }
}
