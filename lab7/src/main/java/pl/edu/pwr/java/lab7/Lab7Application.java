package pl.edu.pwr.java.lab7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pwr.java.lab7.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarFile;

@SpringBootApplication
public class Lab7Application extends MainWindow {

    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
    }
}
