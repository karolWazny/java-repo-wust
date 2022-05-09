package lib;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DirectorySnapshot {
    public final static String version = "alpha 1.0.0";
    List<FileSnapshot> files;
    LocalDateTime timeCalculated;

    public Changes changesSince(DirectorySnapshot previousVersion){
        Changes output = new Changes();
        output.setCurrentCheckTime(timeCalculated);
        output.setPreviousCheckTime(previousVersion.timeCalculated);

        Iterator<FileSnapshot> currentSnaps = files.iterator();
        Iterator<FileSnapshot> previousSnaps = previousVersion.files.iterator();

        FileSnapshot current = null;
        FileSnapshot previous = null;

        Comparator<FileSnapshot> comparator = Comparator.comparing((FileSnapshot fileSnapshot)
                -> fileSnapshot.getFilepath().toString());

        while(currentSnaps.hasNext() && previousSnaps.hasNext()){
            if(current == null)
                current = currentSnaps.next();
            if(previous == null)
                previous = previousSnaps.next();
            int comparisonResult = comparator.compare(current, previous);
            if(comparisonResult == 0){
                if(current.getChecksum().equals(previous.getChecksum())){
                    output.unchangedFiles.add(current.getFilepath());
                } else {
                    output.changedFiles.add(current.getFilepath());
                }
                current = previous = null;
            } else if (comparisonResult > 0){
                output.deletedFiles.add(previous.getFilepath());
                previous = null;
            } else {
                output.addedFiles.add(current.getFilepath());
                current = null;
            }
        }

        while(currentSnaps.hasNext())
            output.addedFiles.add(currentSnaps.next().getFilepath());
        while(previousSnaps.hasNext())
            output.deletedFiles.add(previousSnaps.next().getFilepath());
        return output;
    }

    public List<String> toLines(){
        List<String> output = new LinkedList<>();
        output.add(version);
        output.add(timeCalculated.format(dateTimeFormatter()));
        for (FileSnapshot snap:
             files) {
            output.add(snap.toLine());
        }
        return output;
    }

    private static DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public static DirectorySnapshot fromLines(List<String> lines){
        String[] version = versionFromString(lines.remove(0));
        if(!version[0].equals(version()[0]))
            throw new UnsupportedVersionError();
        LocalDateTime timeGenerated = LocalDateTime.parse(lines.remove(0),
                dateTimeFormatter());
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

    public LocalDateTime getTimeCalculated() {
        return timeCalculated;
    }

    public void setTimeCalculated(LocalDateTime timeCalculated) {
        this.timeCalculated = timeCalculated;
    }

    public static class Changes {
        private LocalDateTime previousCheckTime;
        private LocalDateTime currentCheckTime;

        public List<Path> addedFiles = new LinkedList<>();
        public List<Path> deletedFiles = new LinkedList<>();
        public List<Path> changedFiles = new LinkedList<>();
        public List<Path> unchangedFiles = new LinkedList<>();

        public LocalDateTime getPreviousCheckTime() {
            return previousCheckTime;
        }

        public LocalDateTime getCurrentCheckTime() {
            return currentCheckTime;
        }

        public void setPreviousCheckTime(LocalDateTime previousCheckTime) {
            this.previousCheckTime = previousCheckTime;
        }

        public void setCurrentCheckTime(LocalDateTime currentCheckTime) {
            this.currentCheckTime = currentCheckTime;
        }
    }

    public static class UnsupportedVersionError extends RuntimeException {
        public UnsupportedVersionError() {
            super("Provided file cannot be parsed, because it was created with unsupported version of the library.");
        }
    }
}
