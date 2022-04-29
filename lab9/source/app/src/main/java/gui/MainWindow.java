package gui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class MainWindow extends JFrame {
    private final DataFileChooser fileChooser = new DataFileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooserImpl();

    private Path outputDirectory = Paths.get("");
    private Path fileWithKey;
    private JTextField outputDirTextField;
    private JTextField keyFileTextField;
    private DefaultListModel<Path> inputFilesModel;
    private JList<Path> inputFiles;

    public MainWindow() {
        super();
        setTitle("Szyfrator 2000");

        createFirstPanel();
        createSecondPanel();

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

        inputFilesModel = new DefaultListModel<>();
        inputFiles = new JList<>(inputFilesModel);
        inputFiles.setLayoutOrientation(JList.VERTICAL);
        panel.add(new JScrollPane(inputFiles));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        JButton addFilesButt = new JButton("More files");
        addFilesButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFilesButt.addActionListener(action->this.chooseFilesToEncryptCallback());
        buttons.add(addFilesButt);

        JButton discardButt = new JButton("Discard");
        discardButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        discardButt.addActionListener(action->discardCallback());
        buttons.add(discardButt);

        panel.add(buttons);

        panel.add(new JLabel("Output directory:"));

        outputDirTextField = new JTextField();
        outputDirTextField.setEditable(false);
        outputDirTextField.setText(outputDirectory.toAbsolutePath().toString());
        panel.add(outputDirTextField);

        JButton changeOutputDirButt = new JButton("Change");
        changeOutputDirButt.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeOutputDirButt.addActionListener(action->chooseOutputDirCallback());
        panel.add(changeOutputDirButt);

        add(panel);
    }

    private void discardCallback(){
        int[] selected = inputFiles.getSelectedIndices();
        Arrays.stream(selected)
                .forEach(index->inputFilesModel.remove(index));
    }

    private void chooseFilesToEncryptCallback(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(this);
        chooser.setDialogTitle("Select files to encrypt.");
        File[] files = chooser.getSelectedFiles();
        inputFilesModel.addAll(Arrays.stream(files)
                .map(File::toPath).
                collect(Collectors.toList()));
    }

    private void chooseKeyFile(){
        JFileChooser chooser = fileWithKey == null ? new JFileChooser() : new JFileChooser(fileWithKey.getParent().toString());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(this);
        chooser.setDialogTitle("Select key.");
        File file = chooser.getSelectedFile();
        if(file != null){
            fileWithKey = file.toPath();
            keyFileTextField.setText("" + fileWithKey);
        }
    }

    private void chooseOutputDirCallback(){
        Path newPath = directoryChooser.chooseDirectory(this);
        if(newPath != null){
            outputDirectory = newPath;
            outputDirTextField.setText(outputDirectory.toAbsolutePath().toString());
        }
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JComboBox<String> firstComboBox = new JComboBox<>(new String[]{
                "AES", "RSA"
        });

        panel.add(firstComboBox);

        panel.add(new JLabel("File with key:"));
        keyFileTextField = new JTextField();
        keyFileTextField.setEditable(false);
        panel.add(keyFileTextField);
        JButton chooseKeyButton = new JButton("Choose key");
        chooseKeyButton.addActionListener(action->this.chooseKeyFile());

        JPanel buttons = new JPanel();
        buttons.add(chooseKeyButton);
        buttons.add(new JButton("Encrypt"));
        buttons.add(new JButton("Decrypt"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        panel.add(buttons);

        add(panel);
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JPanel buttons = new JPanel();
        buttons.add(new JButton("Prev"));
        buttons.add(new JButton("Next"));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        panel.add(buttons);

        add(panel);
    }
}