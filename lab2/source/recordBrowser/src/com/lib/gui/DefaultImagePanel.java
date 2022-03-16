package com.lib.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DefaultImagePanel extends ImagePanel{
    protected Dimension dimension;

    public void setDimension(Dimension dimension){
        this.dimension = dimension;
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }

    private BufferedImage image;

    public DefaultImagePanel(){
        super();
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        try {
            g2d.drawImage(image, 0, 0, dimension.width, dimension.height, this);
        } catch (NullPointerException ignored){}
    }
}
