package app;

import lib.gui.MainWindow;
import lib.logic.ApplicationModel;

public class Main {
    public static void main(String[] args){
        ApplicationModel model = new ApplicationModel();
        MainWindow window = new MainWindow(model);
    }
}
