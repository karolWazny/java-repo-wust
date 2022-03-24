package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final static String bundleName = "resources.MainWindowBundle";

    private int currentQuestionIndex = 0;

    public MainWindow() {
        super();
        setTitle("Laboratorium 4");

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setMinimumSize(new Dimension(680, 160));
    }

    public static void main(String[] args){
        new MainWindow();
    }
}