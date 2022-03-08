package lib;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileSnapshot {
    private Path filepath;
    private String checksum;

    public static FileSnapshot fromLine(String line){
        int separatorPosition = line.indexOf(' ');
        String hash = line.substring(0, separatorPosition);
        String pathString = line.substring(separatorPosition);
        Path path = Paths.get(pathString);
        return new FileSnapshot(path, hash);
    }

    public FileSnapshot(Path filepath, String checksum) {
        this.filepath = Objects.requireNonNull(filepath);
        this.checksum = Objects.requireNonNull(checksum);
    }

    public Path getFilepath() {
        return filepath;
    }

    public void setFilepath(Path filepath) {
        this.filepath = Objects.requireNonNull(filepath);
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = Objects.requireNonNull(checksum);
    }

    public String toLine(){
        return checksum + " " + filepath;
    }
}
