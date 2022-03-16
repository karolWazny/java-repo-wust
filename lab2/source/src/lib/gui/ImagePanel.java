package lib.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImagePanel extends JPanel {
    public abstract void setImage(BufferedImage image);
}
