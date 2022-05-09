package lib;

import javax.swing.*;
import java.nio.file.Path;

public interface DirectoryChooser {
    Path chooseDirectory();

    Path chooseDirectory(JFrame parent);
}
