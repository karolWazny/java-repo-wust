package gui;

import javax.swing.*;
import java.awt.event.*;

public class MapPanel extends JPanel implements MouseListener {
    public MapPanel() {

        addMouseListener(this);

        setFocusable(true);
        requestFocus();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            System.out.println("Mouse clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}