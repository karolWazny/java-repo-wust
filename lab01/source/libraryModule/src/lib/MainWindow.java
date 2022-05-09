package lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class MainWindow extends JFrame {
    private JTextArea unchangedFiles = uneditableArea();
    private JTextArea changedFiles = uneditableArea();
    private JTextArea addedFiles = uneditableArea();
    private JTextArea deletedFiles = uneditableArea();

    private DirectoryChooser dirChooser = new DirectoryChooserImpl();
    private SnapshotCreator snapshotCreator = new SnapshotCreatorImpl();
    private SnapshotFileHandler fileHandler;

    private final JTextField directoryTextField;
    private JButton browseButton;
    private JTextField metaField;

    private DirectorySnapshot currentSnapshot;
    private DirectorySnapshot previousSnapshot;
    private DirectorySnapshot.Changes changes;

    private Path directory;

    public void setDirectory(Path directory) {
        this.directory = directory;
        fileHandler = new SnapshotFileHandler(directory.resolve(".snapshot"));
        try {
            createSnapshot();
            readPreviousSnapshot();
            compareSnapshots();
            updateDisplayedValues();
            saveCurrentSnapshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCurrentSnapshot() throws IOException {
        fileHandler.write(currentSnapshot);
    }

    private void createSnapshot() throws IOException {
        currentSnapshot = snapshotCreator.snapshotOf(directory);
    }

    private void readPreviousSnapshot() throws IOException {
        previousSnapshot = fileHandler.read();
    }

    private void compareSnapshots(){
        if(previousSnapshot == null){
            changes = new DirectorySnapshot.Changes();
        } else {
            changes = currentSnapshot.changesSince(previousSnapshot);
        }
    }

    private void updateDisplayedValues(){
        if(changes.getPreviousCheckTime() == null) {
            metaField.setText("This directory has never been checked before.");
        } else {
            metaField.setText("Last checked: " + changes.getPreviousCheckTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
        directoryTextField.setText(directory.toString());
        StringBuilder buffer = new StringBuilder();
        for(Path path : changes.unchangedFiles){
            buffer.append(path.toString()).append('\n');
        }
        unchangedFiles.setText(buffer.toString().trim());

        buffer = new StringBuilder();
        for(Path path : changes.changedFiles){
            buffer.append(path.toString()).append('\n');
        }
        changedFiles.setText(buffer.toString().trim());

        buffer = new StringBuilder();
        for(Path path : changes.addedFiles){
            buffer.append(path.toString()).append('\n');
        }
        addedFiles.setText(buffer.toString().trim());

        buffer = new StringBuilder();
        for(Path path : changes.deletedFiles){
            buffer.append(path.toString()).append('\n');
        }
        deletedFiles.setText(buffer.toString().trim());
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Unchanged files:"));
        bottomPanel.add(unchangedFiles);
        bottomPanel.add(new JLabel("Changed files:"));
        bottomPanel.add(changedFiles);
        bottomPanel.add(new JLabel("Added files:"));
        bottomPanel.add(addedFiles);
        bottomPanel.add(new JLabel("Deleted files:"));
        bottomPanel.add(deletedFiles);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));

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
