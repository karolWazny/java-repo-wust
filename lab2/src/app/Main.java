package app;

import lib.DirectoryChooser;
import lib.DirectoryChooserImpl;
import lib.RecordsLister;

public class Main {
    public static void main(String[] args){
        DirectoryChooser chooser = new DirectoryChooserImpl();
        RecordsLister lister = new RecordsLister(chooser.chooseDirectory());
        System.out.println(lister.listRecords());
    }
}
