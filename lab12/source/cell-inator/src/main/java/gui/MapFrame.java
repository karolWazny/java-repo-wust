package gui;

import engine.Engine;

import javax.swing.*;
import java.awt.*;

public class MapFrame extends JFrame {
    public MapFrame(Engine engine) {
        super();
        setTitle("Cell-inator: run " + engine.engineName());

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new MapPanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(850, 220));
    }
}
