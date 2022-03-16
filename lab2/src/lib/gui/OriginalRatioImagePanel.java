package lib.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OriginalRatioImagePanel extends ImagePanel{
    private BufferedImage image;
    double height;

    public OriginalRatioImagePanel(){
        super();
    }

    public void setHeight(int height){
        this.height = height;
        setDimension(getDimension());
    }

    public void setWidth(int width){
        this.height = (int) (getHeightToWidthRatio() * width);
        setDimension(getDimension());
    }

    private Dimension getDimension(){
        return new Dimension((int)(getWidthToHeightRatio() * height), (int)height);
    }

    private void setDimension(Dimension dimension){
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setSize(dimension);
    }

    private double getWidthToHeightRatio(){
        try{
            double w = image.getWidth();
            double h = image.getHeight();
            return w / h;
        } catch (NullPointerException e){
            return 1.0;
        }
    }

    private double getHeightToWidthRatio(){
        return 1 / getWidthToHeightRatio();
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
        setDimension(getDimension());
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        try {
            g2d.drawImage(image, 0, 0, (int)(getWidthToHeightRatio() * height), (int)height, this);
        } catch (NullPointerException ignored){
            ignored.printStackTrace();
        }
    }
}
