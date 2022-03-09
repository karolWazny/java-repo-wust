package lib;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class DirectoryChooserImpl implements DirectoryChooser {
    @Override
    public Path chooseDirectory(){
        return chooseDirectory(null);
    }

    @Override
    public Path chooseDirectory(JFrame parent){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(parent);
        chooser.setDialogTitle("Select directory.");
        File file = chooser.getSelectedFile();
        return file == null ? null : file.toPath();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        JPanel panel = new JPanel();
        JButton butt = new JButton("Button 1");
        panel.add(butt);
        panel.setPreferredSize(new Dimension(200, 200));
        frame.setContentPane(panel);
        frame.setVisible(true);

        DirectoryChooserImpl chooser = new DirectoryChooserImpl();
        System.out.println(chooser.chooseDirectory());
    }
}
