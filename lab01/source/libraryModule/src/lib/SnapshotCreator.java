package lib;

import java.io.IOException;
import java.nio.file.Path;

public interface SnapshotCreator {
    DirectorySnapshot snapshotOf(Path folderPath) throws IOException;
}
