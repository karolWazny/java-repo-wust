package lib;

import java.nio.file.Path;
import java.sql.Time;
import java.util.List;

public class DirectorySnapshot {
    Path directory;
    List<FileSnapshot> files;
    Time timeCalculated;

    public Changes changesSince(DirectorySnapshot previousVersion){
        return null;
    }

    public DirectorySnapshot(Path directory) {
        this.directory = directory;
    }

    public Path getDirectory() {
        return directory;
    }

    public void setDirectory(Path directory) {
        this.directory = directory;
    }

    public List<FileSnapshot> getFiles() {
        return files;
    }

    public void setFiles(List<FileSnapshot> files) {
        this.files = files;
    }

    public Time getTimeCalculated() {
        return timeCalculated;
    }

    public void setTimeCalculated(Time timeCalculated) {
        this.timeCalculated = timeCalculated;
    }

    public static class Changes {
        public Time previousCheckTime;
        public Time currentCheckTime;

        public List<Path> addedFiles;
        public List<Path> deletedFiles;
        public List<Path> changedFiles;
        public List<Path> unchangedFiles;
    }
}
