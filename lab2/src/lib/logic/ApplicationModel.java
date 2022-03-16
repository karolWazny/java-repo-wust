package lib.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public class ApplicationModel {
    private final RecordsLister recordsLister = new RecordsLister();

    private final Map<String, Record> cachedRecords = new WeakHashMap<>();
    private boolean lastRecordFromCache = false;

    public List<String> recordNames(){
        return recordsLister.listRecords()
                .stream()
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public void setDirectory(Path directory) {
        recordsLister.setDirectory(directory);
    }

    public Path getDirectory(){
        return recordsLister.getDirectory();
    }

    public Record getRecord(String key){
        if(cachedRecords.containsKey(key)){
            lastRecordFromCache = true;
            return cachedRecords.get(key);
        }
        RecordLoader loader = new RecordLoader();
        Record record = loader.load(getDirectory().resolve(key));
        cachedRecords.put(key, record);
        lastRecordFromCache = false;
        return record;
    }

    public boolean wasLastRecordInCache(){
        return lastRecordFromCache;
    }
}
