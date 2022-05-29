module pl.edu.pwr.java.lab13.jsfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.reflections;

    opens pl.edu.pwr.java.lab13.jsfx to javafx.fxml;
    exports pl.edu.pwr.java.lab13.jsfx;
}