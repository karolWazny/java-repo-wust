package gui;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindow extends JFrame {

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

        panel.add(new MapPanel());

        add(panel);
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("Current script:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Scripts"));

        JList<Path> paths = new JList<>(new Path[]{
                Paths.get("piwo.js"),
                Paths.get("dupa.js"),
        });
        panel.add(new JScrollPane(paths));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.add(new JButton("Find more"));
        buttons.add(new JButton("Use chosen"));
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
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.PAGE_AXIS));
        loadPanel.add(new JLabel("Loaded map:"));
        loadPanel.add(new JTextField());
        loadPanel.add(new JLabel("Engine type:"));
        loadPanel.add(new JTextField());
        loadPanel.add(new JLabel("Height:"));
        loadPanel.add(new JTextField());
        loadPanel.add(new JLabel("Width:"));
        loadPanel.add(new JTextField());
        loadPanel.add(new JButton("Load from file"));

        panel.add(loadPanel);

        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.PAGE_AXIS));
        createPanel.add(new JLabel("Height:"));
        createPanel.add(new JTextField());
        createPanel.add(new JLabel("Width:"));
        createPanel.add(new JTextField());

        panel.add(createPanel);

        comboBox.addActionListener(event->{
            if(comboBox.getSelectedItem().equals("Create")){
                createPanel.setVisible(true);
                loadPanel.setVisible(false);
            } else {
                createPanel.setVisible(false);
                loadPanel.setVisible(true);
            }
        });

        comboBox.setSelectedIndex(0);

        panel.add(new JButton("Start"));

        add(panel);
    }
}
