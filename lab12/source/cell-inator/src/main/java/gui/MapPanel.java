package gui;

import engine.Engine;
import engine.Map;
import engine.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapPanel extends JPanel implements MouseListener {
    private final List<Double> gridXPoints;
    private final List<Double> gridYPoints;

    private final double minX = 10;
    private final double minY = 10;

    private final Double cellSize = 20.0;

    private final double maxX;
    private final double maxY;
    private final int width;
    private final int height;

    private final Engine engine;
    private final Map map;

    public MapPanel(Engine engine) {
        this.engine = engine;
        this.map = engine.getMap();
        height = this.map.getHeight();
        width = this.map.getWidth();

        maxY = minY + cellSize * height;
        maxX = minX + cellSize * width;


        setPreferredSize(new Dimension((int) (minX + maxX), (int) (minY + maxY)));


        gridYPoints = IntStream.range(0, height + 2)
                .mapToDouble(index-> minY + index * cellSize)
                .boxed()
                .collect(Collectors.toList());

        gridXPoints = IntStream.range(0, width + 2)
                .mapToDouble(index-> minX + index * cellSize)
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
                                g.setColor(engine.colorOnPosition(new Position(x, y)));
                                g.fillRect(gridXPoints.get(x).intValue(),
                                        gridYPoints.get(y).intValue(),
                                        cellSize.intValue(),
                                        cellSize.intValue());
                            });
                });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try{
            if(e.getButton() == MouseEvent.BUTTON1) {
                Position position = Objects.requireNonNull(positionFromClick(e));
                engine.changeStateOnPosition(position);
                repaint();
            }
        } catch (NullPointerException ignored){}
    }

    private Position positionFromClick(MouseEvent e){
        if(e.getY() <= minY || e.getY() >= maxY || e.getX() <= minX || e.getX() >= maxX){
            return null;
        } else {
            return new Position((int) ((e.getX() - minX) / cellSize), (int) ((e.getY() - minY) / cellSize));
        }
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