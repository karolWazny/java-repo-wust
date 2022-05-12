package gui;

import api.Encryption;
import api.StreamDecryptor;
import api.StreamEncryptor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collections;
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

    private DefaultComboBoxModel<String> aliasesEncryptModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<String> aliasesDecryptModel = new DefaultComboBoxModel<>();

    private final Encryption encryption = Encryption.getInstance();
    private JComboBox<StreamEncryptor> encryptorComboBox;
    private JComboBox<StreamDecryptor> decryptorJComboBox;

    public MainWindow() {
        super();
        setTitle("Szyfrator 2000 - Java 8");

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

        panel.add(new JLabel("Keystore:"));
        keyFileTextField = new JTextField();
        keyFileTextField.setEditable(false);
        panel.add(keyFileTextField);
        JButton chooseKeyButton = new JButton("Choose keystore");
        chooseKeyButton.addActionListener(action-> {
            try {
                this.chooseKeyStoreCallback();
            } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
                log.error(e.toString());
            }
        });
        panel.add(chooseKeyButton);

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

        for(File file : files){
            inputFilesModel.addElement(file.toPath());
        }
    }

    private void chooseKeyStoreCallback() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        FileDialog fileDialog = new FileDialog(this, "Select keystore");
        fileDialog.setVisible(true);

        String file = fileDialog.getFile();
        char[] password;
        if(file != null) {
            Path keystoreFile = Paths.get(fileDialog.getDirectory() + file);
            password = PasswordDialog.show();
            try {
                encryption.setKeystore(keystoreFile, password);
                fileWithKey = keystoreFile;
                keyFileTextField.setText("" + fileWithKey);
                aliasesDecryptModel.removeAllElements();
                for(String alias : Collections.list(encryption.keysAliases())){
                    aliasesDecryptModel.addElement(alias);
                }
                aliasesEncryptModel.removeAllElements();
                for(String alias : Collections.list(encryption.keysAliases())){
                    aliasesEncryptModel.addElement(alias);
                }
            } finally {
                Arrays.fill(password, '0');
            }
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
        panel.add(new JLabel("Encrypt files"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        encryptorComboBox = new JComboBox<>(encryption.availableStreamEncryptors()
                .toArray(new StreamEncryptor[0]));

        panel.add(encryptorComboBox);

        JComboBox<String> aliasesBoxEncrypt = new JComboBox<>(aliasesEncryptModel);
        panel.add(aliasesBoxEncrypt);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(action->encryptFilesCallback());
        panel.add(encryptButton);

        add(panel);
    }

    private void encryptFilesCallback() {
        log.info("Encrypting files...");
        log.info("Chosen algorithm: " + encryptorComboBox.getSelectedItem());

        StreamEncryptor encryptor = (StreamEncryptor) encryptorComboBox.getSelectedItem();
        String keyAlias = (String) aliasesEncryptModel.getSelectedItem();
        Key key;

        try {
            key = encryption.retrieveKey(keyAlias, PasswordDialog.show(), encryptor.keyType());
            encryptor.setKey(key);
            key = null;

            for(Path path : inputFiles.getSelectedValuesList()) {
                String filename = path.getFileName().toString() + ".enc";
                Path outputFile = outputDirectory.resolve(filename);

                try (InputStream inputStream = new FileInputStream(String.valueOf(path));
                     OutputStream outputStream = new FileOutputStream(String.valueOf(outputFile))) {
                    encryptor.encrypt(inputStream, outputStream);
                }

                log.info("Encrypted file: " + outputFile);
            }

            log.info("Encrypted chosen files.");
        } catch (IOException | InvalidKeyException e) {
            log.info(e.toString());
            e.printStackTrace();
        } catch (UnrecoverableEntryException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
    }

    private void decryptFilesCallback(){
        log.info("Decrypting files...");
        log.info("Chosen algorithm: " + decryptorJComboBox.getSelectedItem());

        StreamDecryptor decryptor = (StreamDecryptor) decryptorJComboBox.getSelectedItem();
        String keyAlias = (String) aliasesDecryptModel.getSelectedItem();
        Key key;

        try {
            key = encryption.retrieveKey(keyAlias, PasswordDialog.show(), decryptor.keyType());
            decryptor.setKey(key);
            key = null;

            for(Path path : inputFiles.getSelectedValuesList()) {
                String filename = path.getFileName().toString();
                filename = filename.replaceAll("\\.enc\\z", "");

                Path outputFile = outputDirectory.resolve(filename);

                try (InputStream inputStream = new FileInputStream(String.valueOf(path));
                     OutputStream outputStream = new FileOutputStream(String.valueOf(outputFile))) {
                    decryptor.decrypt(inputStream, outputStream);
                }
                log.info("Decrypted file: " + outputFile);
            }
        } catch (InvalidKeyException | IOException | InvalidAlgorithmParameterException | UnrecoverableEntryException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        log.info("Decrypted chosen files.");
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Decrypt files"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        decryptorJComboBox = new JComboBox<>(encryption.availableStreamDecryptors()
                .toArray(new StreamDecryptor[0]));

        panel.add(decryptorJComboBox);

        JComboBox<String> aliasesBoxDecrypt = new JComboBox<>(aliasesDecryptModel);
        panel.add(aliasesBoxDecrypt);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(action->decryptFilesCallback());
        panel.add(decryptButton);

        add(panel);
    }
}