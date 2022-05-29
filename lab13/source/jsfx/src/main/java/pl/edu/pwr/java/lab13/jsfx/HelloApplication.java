package pl.edu.pwr.java.lab13.jsfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        ChoiceBox<String> box = new ChoiceBox<>();
        box.getItems().add("");

        Reflections reflections = new Reflections("templates", Scanners.Resources);
        Set<String> resources = reflections.getResources(Pattern.compile(".*\\.xml"));
        resources
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        launch();
    }
}