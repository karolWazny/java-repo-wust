package gui;

import javax.swing.*;
import java.awt.*;

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



        add(panel);
    }

    private void createSecondPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));



        add(panel);
    }


    private void createThirdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));



        add(panel);
    }
}
