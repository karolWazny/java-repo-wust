package lib;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;

public class DirectoryChooser {
    public Path chooseDirectory(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        return file == null ? null : file.toPath();
    }

    public static void main(String[] args){
        DirectoryChooser chooser = new DirectoryChooser();
        System.out.println(chooser.chooseDirectory());
    }
}
