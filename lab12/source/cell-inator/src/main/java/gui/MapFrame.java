package gui;

import engine.Engine;

import javax.swing.*;

public class MapFrame extends JFrame {
    public MapFrame(Engine engine) {
        super();
        setTitle("Cell-inator: run " + engine.engineName());

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new MapPanel(engine));
        add(new JSeparator());
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.add(new JButton("Start"));
        buttons.add(new JButton("Stop"));
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(action->{
            engine.step();
            repaint();
        });
        buttons.add(stepButton);
        buttons.add(new JButton("Save"));
        add(buttons);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
