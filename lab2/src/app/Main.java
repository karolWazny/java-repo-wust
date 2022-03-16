package app;

import lib.logic.DirectoryChooser;
import lib.logic.DirectoryChooserImpl;
import lib.logic.RecordsLister;

public class Main {
    public static void main(String[] args){
        DirectoryChooser chooser = new DirectoryChooserImpl();
        RecordsLister lister = new RecordsLister(chooser.chooseDirectory());
        System.out.println(lister.listRecords());
    }
}
