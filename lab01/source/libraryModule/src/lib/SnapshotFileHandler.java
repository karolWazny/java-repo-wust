package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class SnapshotFileHandler {
    private final Path file;

    public SnapshotFileHandler(Path file) {
        this.file = Objects.requireNonNull(file);
    }

    public DirectorySnapshot read() throws IOException {
        try{
            List<String> lines = Files.readAllLines(file);
            return DirectorySnapshot.fromLines(lines);
        } catch (NoSuchFileException e){
            return null;
        }
    }

    public void write(DirectorySnapshot snapshot) throws IOException {
        StringBuilder buffer = new StringBuilder();
        List<String> lines = snapshot.toLines();
        for (String line:
             lines) {
            buffer.append(line).append('\n');
        }
        Files.writeString(file, buffer.toString());
    }

    public Path getFile() {
        return file;
    }
}
