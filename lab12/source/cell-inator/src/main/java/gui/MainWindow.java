package gui;

import engine.Engine;
import engine.Map;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {
    private Path chosenScript;

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
        System.out.println(Paths.get("").toAbsolutePath());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Current script:"));
        JTextField currentScriptField = new JTextField();
        currentScriptField.setEditable(false);
        panel.add(currentScriptField);

        panel.add(new JLabel("Scripts"));

        JList<Path> paths = null;
        try {
            paths = new JList<>(Files.walk(Paths.get(""), Integer.MAX_VALUE)
                    .filter(path -> path.getFileName().toString().endsWith(".js"))
                    .collect(Collectors.toList())
                    .toArray(Path[]::new));
        } catch (IOException e) {
            e.printStackTrace();
            paths = new JList<>();
        }
        paths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(paths));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(new JButton("Find more"));
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
        JTextField heightField = new JTextField();
        createPanel.add(heightField);
        createPanel.add(new JLabel("Width:"));
        JTextField widthField = new JTextField();
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
            new MapFrame(new Engine(new Map(height, width)));
        });
        panel.add(startButton);

        add(panel);
    }
}
