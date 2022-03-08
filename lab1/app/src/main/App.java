package main;

import lib.DirectoryChooser;
import lib.DirectorySnapshot;
import lib.SnapshotCreator;
import lib.SnapshotFileHandler;

import java.io.IOException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        Path directory = chooser.chooseDirectory();
        SnapshotCreator snapper = new SnapshotCreator();
        DirectorySnapshot snap = snapper.snapshotOf(directory);
        SnapshotFileHandler fileHandler = new SnapshotFileHandler(directory.resolve(".snapshot"));
        fileHandler.write(snap);
    }
}
