package lib;

import java.nio.file.Path;

public class FileSnapshot {
    private Path filepath;
    private String checksum;

    public FileSnapshot(Path filepath, String checksum) {
        this.filepath = filepath;
        this.checksum = checksum;
    }

    public Path getFilepath() {
        return filepath;
    }

    public void setFilepath(Path filepath) {
        this.filepath = filepath;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
