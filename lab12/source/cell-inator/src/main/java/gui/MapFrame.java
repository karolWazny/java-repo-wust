package gui;

import engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MapFrame extends JFrame implements MouseMotionListener, MouseListener {
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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("dupa");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
