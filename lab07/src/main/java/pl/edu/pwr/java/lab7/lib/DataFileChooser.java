package pl.edu.pwr.java.lab7.lib;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataFileChooser {
    private Path lastChoice = Paths.get("");

    public Path chooseFile(){
        return chooseFile(null);
    }

    public Path chooseFile(JFrame parent){
        JFileChooser chooser = lastChoice == null ? new JFileChooser() : new JFileChooser("" + lastChoice);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
        chooser.showOpenDialog(parent);
        chooser.setDialogTitle("Select directory.");
        File file = chooser.getSelectedFile();
        if(file == null){
            return null;
        } else {
            lastChoice = file.toPath();
            return lastChoice;
        }
    }

    public static void main(String[] args){
        DataFileChooser chooser = new DataFileChooser();
        System.out.println(chooser.chooseFile());
        System.out.println(chooser.chooseFile());
        System.out.println(chooser.chooseFile());
    }
}
