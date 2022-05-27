package gui;

import engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapPanel extends JPanel implements MouseListener {
    private final List<Double> gridXPoints;
    private final List<Double> gridYPoints;

    private final double minX = 10;
    private final double minY = 10;

    private double maxX;
    private double maxY = 390;

    public MapPanel(Engine engine) {
        int height = engine.getMap().getHeight();
        int width = engine.getMap().getWidth();

        maxY = 10 + 40 * height;
        maxX = 10 + 40 * width;


        setPreferredSize(new Dimension((int) (10 + maxX), (int) (10 + maxY)));


        gridYPoints = IntStream.range(0, height + 2)
                .mapToDouble(index-> 10 + index * 40)
                .boxed()
                .collect(Collectors.toList());

        gridXPoints = IntStream.range(0, width + 2)
                .mapToDouble(index-> 10 + index * 40)
                .boxed()
                .collect(Collectors.toList());

        addMouseListener(this);

        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        paintGrid(g2d);
    }

    private void paintGrid(Graphics2D g){
        gridXPoints
                .forEach(element ->{
                    Line2D line2D = new Line2D.Double(element, minY, element, maxY);
                    g.draw(line2D);
                });
        gridYPoints
                .forEach(element ->{
                    Line2D line2D = new Line2D.Double(minX, element, maxX, element);
                    g.draw(line2D);
                });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            System.out.println(e.getX() + " " + e.getY());
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