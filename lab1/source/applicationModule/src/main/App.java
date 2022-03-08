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
        DirectorySnapshot previous = fileHandler.read();
        if(previous != null){
            DirectorySnapshot.Changes changes = snap.changesSince(previous);
        } else {
            System.out.println("This directory does not contain a '.snapshot' file.\n" +
                    "Cannot tell what changed. It will be possible next time around.");
        }
        fileHandler.write(snap);
    }
}
