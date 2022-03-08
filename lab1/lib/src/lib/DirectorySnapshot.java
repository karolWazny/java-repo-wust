package lib;

import java.nio.file.Path;
import java.sql.Time;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DirectorySnapshot {
    public final static String version = "alpha 0.0.0";
    List<FileSnapshot> files;
    Time timeCalculated;

    public Changes changesSince(DirectorySnapshot previousVersion){
        return null;
    }

    public List<String> toLines(){
        List<String> output = new LinkedList<>();
        output.add(version);
        output.add(timeCalculated.toString());
        for (FileSnapshot snap:
             files) {
            output.add(snap.toLine());
        }
        return output;
    }

    public static DirectorySnapshot fromLines(List<String> lines){
        String[] version = versionFromString(lines.remove(0));
        if(!version[0].equals(version()[0]))
            throw new UnsupportedVersionError();
        Time timeGenerated = Time.valueOf(lines.remove(0));
        List<FileSnapshot> fileSnapshots = new LinkedList<>();
        for (String line:
             lines) {
            fileSnapshots.add(FileSnapshot.fromLine(line));
        }

        DirectorySnapshot output = new DirectorySnapshot();
        output.setFiles(fileSnapshots);
        output.setTimeCalculated(timeGenerated);

        return output;
    }

    private static String[] versionFromString(String version){
        String[] output = new String[3];
        return version.split("\\.");
    }

    private static String[] version(){
        return versionFromString(version);
    }

    public void setFiles(List<FileSnapshot> files) {
        this.files = files;
        this.files.sort(Comparator.comparing((FileSnapshot fileSnapshot) -> fileSnapshot.getFilepath().toString()));
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

    public static class UnsupportedVersionError extends RuntimeException {
        public UnsupportedVersionError() {
            super("Provided file cannot be parsed, because it was created with unsupported version of the library.");
        }
    }
}
