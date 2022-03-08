package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnapshotCreator {
    private Path folder;
    private Set<Path> paths;
    private FileHasher hasher = new FileHasher();
    private List<FileSnapshot> fileSnapshots;
    private Time timeGenerated;

    public DirectorySnapshot snapshotOf(Path folderPath) throws IOException {
        this.folder = folderPath;
        prepareTimeStamp();
        listFiles();
        generateFileSnapshots();
        return buildDirSnapshot();
    }

    private void prepareTimeStamp(){
        timeGenerated = Time.valueOf(LocalTime.now());
    }

    private void listFiles() throws IOException {
        Stream<Path> contentStream = Files.walk(folder)
                .filter(Files::isRegularFile)
                .filter((Path path)-> !path.getFileName().toString().equals(".snapshot"));
        //.filter(Files::isReadable);
        paths = contentStream.collect(Collectors.toSet());
    }

    private void generateFileSnapshots(){
        fileSnapshots = new LinkedList<>();
        for(Path path : paths){
            fileSnapshots.add(fileSnapshot(folder.relativize(path)));
        }
    }

    private DirectorySnapshot buildDirSnapshot(){
        DirectorySnapshot directorySnapshot = new DirectorySnapshot();
        directorySnapshot.setTimeCalculated(timeGenerated);
        directorySnapshot.setFiles(fileSnapshots);
        return directorySnapshot;
    }

    private FileSnapshot fileSnapshot(Path file) {
        Path absolutePath = this.folder.resolve(file);
        String hash = this.hasher.hashValueOf(absolutePath);
        return new FileSnapshot(file, hash);
    }

    public static void main(String[] args) throws Exception {
        SnapshotCreator creator = new SnapshotCreator();
        DirectoryChooser chooser = new DirectoryChooser();
        System.out.println(creator.snapshotOf(chooser.chooseDirectory()));
    }
}
