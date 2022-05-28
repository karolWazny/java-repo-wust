package gui;

import engine.Engine;
import engine.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {
    private Path chosenScript;
    private final ScriptEngineManager engineManager = new ScriptEngineManager();

    private Path lastLocation = Paths.get("").toAbsolutePath();

    public MainWindow() {
        super();
        setTitle("Cell-inator");

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

        add(panel);
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Current script:"));
        JTextField currentScriptField = new JTextField();
        currentScriptField.setEditable(false);
        panel.add(currentScriptField);

        panel.add(new JLabel("Scripts"));

        DefaultListModel<Path> listModel = new DefaultListModel<>();
        JList<Path> paths = new JList<>(listModel);
        try {
            listModel.addAll(scriptsInDirectory(this.lastLocation));
        } catch (IOException e) {
            e.printStackTrace();
            paths = new JList<>();
        }
        paths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(paths));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        JButton findScriptsButton = new JButton("Find more");
        findScriptsButton.addActionListener(action->{
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setCurrentDirectory(lastLocation.toFile());
            chooser.showOpenDialog(this);
            try {
                File chosen = Objects.requireNonNull(chooser.getSelectedFile());
                if(chosen.isDirectory()){
                    lastLocation = chosen.toPath();
                    listModel.addAll(scriptsInDirectory(lastLocation));
                } else {
                    lastLocation = chosen.toPath();
                    if(lastLocation.toString().endsWith(".js"))
                        listModel.addElement(lastLocation);
                }
            } catch (NullPointerException | IOException e){
                e.printStackTrace();
            }
        });
        buttons.add(findScriptsButton);
        JButton useChosenButton = new JButton("Use chosen");
        JList<Path> finalPaths = paths;
        useChosenButton.addActionListener(action->{
            try{
                this.chosenScript = Objects.requireNonNull(finalPaths.getSelectedValue());
                currentScriptField.setText("" + this.chosenScript);
            } catch (NullPointerException ignored){

            }

        });
        buttons.add(useChosenButton);
        panel.add(buttons);

        add(panel);
    }

    private List<Path> scriptsInDirectory(Path directory) throws IOException {
        return Files.walk(Paths.get(""), Integer.MAX_VALUE)
                .filter(path -> path.getFileName().toString().endsWith(".js"))
                .collect(Collectors.toList());
    }

    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "Create",
                "Load from file"
        });

        panel.add(comboBox);

        JPanel loadPanel = new JPanel();
        {
            loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.PAGE_AXIS));
            loadPanel.add(new JLabel("Loaded map:"));
            JTextField mapField = new JTextField();
            mapField.setEditable(false);
            loadPanel.add(mapField);
            loadPanel.add(new JLabel("Engine type:"));
            JTextField engineField = new JTextField();
            engineField.setEditable(false);
            loadPanel.add(engineField);
            loadPanel.add(new JLabel("Height:"));
            JTextField heightField = new JTextField();
            heightField.setEditable(false);
            loadPanel.add(heightField);
            loadPanel.add(new JLabel("Width:"));
            JTextField widthField = new JTextField();
            widthField.setEditable(false);
            loadPanel.add(widthField);
            loadPanel.add(new JButton("Load from file"));
        }

        panel.add(loadPanel);

        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));
        createPanel.add(new JLabel("Height:"));
        JTextField heightField = new JTextField("30");
        createPanel.add(heightField);
        createPanel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField("50");
        createPanel.add(widthField);

        panel.add(createPanel);

        comboBox.addActionListener(event->{
            if(Objects.equals(comboBox.getSelectedItem(), "Create")){
                createPanel.setVisible(true);
                loadPanel.setVisible(false);
            } else {
                createPanel.setVisible(false);
                loadPanel.setVisible(true);
            }
        });

        comboBox.setSelectedIndex(0);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(action -> {
            int height = Integer.parseInt(heightField.getText().trim());
            int width = Integer.parseInt(widthField.getText().trim());
            try {
                new MapFrame(new Engine(getJSMachine(), new Map(height, width, chosenScript.getFileName().toString())));
            } catch (FileNotFoundException | NullPointerException | ScriptException ignored){
                ignored.printStackTrace();
            }
        });
        panel.add(startButton);

        add(panel);
    }

    private Invocable getJSMachine() throws FileNotFoundException, ScriptException {
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");
        engine.eval(new InputStreamReader(new FileInputStream(chosenScript.toFile())));
        return (Invocable) engine;
    }
}
