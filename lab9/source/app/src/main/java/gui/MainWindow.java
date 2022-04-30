package gui;

import api.Encryption;
import api.StreamDecryptor;
import api.StreamEncryptor;
import lombok.extern.slf4j.Slf4j;
import sampleImplementation.StreamDecryptorImpl;
import sampleImplementation.StreamEncryptorImpl;

import javax.crypto.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class MainWindow extends JFrame {
    private final DirectoryChooser directoryChooser = new DirectoryChooserImpl();

    private Path outputDirectory = Paths.get("");
    private Path fileWithKey;
    private JTextField outputDirTextField;
    private JTextField keyFileTextField;
    private DefaultListModel<Path> inputFilesModel;
    private JList<Path> inputFiles;

    private SecretKey key;

    private final Encryption encryption = Encryption.getInstance();

    {
        try {
            key = KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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

        FileDialog fileDialog = new FileDialog(this);
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);
        File[] files = fileDialog.getFiles();

        inputFilesModel.addAll(Arrays.stream(files)
                .map(File::toPath).
                collect(Collectors.toList()));
    }

    private void chooseKeyFile(){
        FileDialog fileDialog = new FileDialog(this, "Select key.");
        fileDialog.setVisible(true);

        String file =fileDialog.getFile();
        if(file != null) {
            fileWithKey = Path.of(file);
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
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(action->encryptFilesCallback());
        buttons.add(encryptButton);
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(action->decryptFilesCallback());
        buttons.add(decryptButton);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));

        panel.add(buttons);

        add(panel);
    }

    private void encryptFilesCallback() {
        log.info("Encrypting chosen files...");

        for(int i = 0; i < inputFilesModel.getSize(); i++){
            Path path = inputFilesModel.get(i);
            String filename = path.getFileName().toString() + ".enc";
            Path outputFile = outputDirectory.resolve(filename);

            try {
                InputStream inputStream = new FileInputStream(String.valueOf(path));
                OutputStream outputStream = new FileOutputStream(String.valueOf(outputFile));
                StreamEncryptor encryptor = new StreamEncryptorImpl();
                encryptor.setKey(key);
                encryptor.encrypt(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            } catch (IOException | InvalidKeyException e) {
                e.printStackTrace();
            }

        }

        log.info("Encrypted chosen files.");
    }

    private void decryptFilesCallback(){
        log.info("Decrypting chosen files...");

        for(int i = 0; i < inputFilesModel.getSize(); i++){
            Path path = inputFilesModel.get(i);
            String filename = path.getFileName().toString();
            filename = filename.replaceAll("\\.enc\\z", "");

            Path outputFile = outputDirectory.resolve(filename);

            try {
                InputStream inputStream = new FileInputStream(String.valueOf(path));
                OutputStream outputStream = new FileOutputStream(String.valueOf(outputFile));
                StreamDecryptor encryptor = new StreamDecryptorImpl();
                encryptor.setKey(key);
                encryptor.decrypt(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            } catch (InvalidKeyException| IOException| InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

        }

        log.info("Decrypted chosen files.");
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