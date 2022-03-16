package lib.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordsLister {
    private Path directory;

    public RecordsLister() {}

    public RecordsLister(Path directory) {
        this.directory = Objects.requireNonNull(directory);
    }

    public Path getDirectory() {
        return directory;
    }

    public void setDirectory(Path directory) {
        this.directory = Objects.requireNonNull(directory);
    }

    public List<Path> listRecords() {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream
                    .filter(file -> Files.exists(file.resolve("record.txt")))
                    .filter(file -> Files.exists(file.resolve("image.png")))
                    .map((Path dir)-> directory.relativize(dir))
                    .collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Something went wrong with reading records from filesystem.");
    }
}
