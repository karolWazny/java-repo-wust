package com.app;

import com.lib.gui.MainWindow;
import com.lib.logic.ApplicationModel;

public class Main {
    public static void main(String[] args){
        ApplicationModel model = new ApplicationModel();
        MainWindow window = new MainWindow(model);
    }
}
