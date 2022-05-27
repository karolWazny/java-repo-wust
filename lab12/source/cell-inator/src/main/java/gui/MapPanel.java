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

    private final Double cellSize = 40.0;

    private final double maxX;
    private final double maxY;
    private final int width;
    private final int height;

    public MapPanel(Engine engine) {
        height = engine.getMap().getHeight();
        width = engine.getMap().getWidth();

        maxY = minY + cellSize * height;
        maxX = minX + cellSize * width;


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

        paintCells(g2d);
        paintGrid(g2d);
    }

    private void paintGrid(Graphics2D g){
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(2));
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

    private void paintCells(Graphics2D g){
        IntStream.range(0, width)
                .forEach(x-> {
                    IntStream.range(0, height)
                            .forEach(y->{
                                g.setColor(Color.CYAN);
                                g.fillRect(gridXPoints.get(x).intValue(),
                                        gridYPoints.get(y).intValue(),
                                        cellSize.intValue(),
                                        cellSize.intValue());
                            });
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