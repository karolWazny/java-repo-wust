package app;

import lib.DirectoryChooser;
import lib.DirectoryChooserImpl;

public class Main {
    public static void main(String[] args){
        DirectoryChooser chooser = new DirectoryChooserImpl();
        System.out.println(chooser.chooseDirectory());
    }
}
