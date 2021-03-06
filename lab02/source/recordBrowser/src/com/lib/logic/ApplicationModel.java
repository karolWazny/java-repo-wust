package com.lib.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationModel {
    private final RecordsLister recordsLister = new RecordsLister();

    private final Map<Key, Record> cachedRecords = new WeakHashMap<>();
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
        if(cachedRecords.containsKey(new Key(key))){
            lastRecordFromCache = true;
            return cachedRecords.get(new Key(key));
        }
        try{
            RecordLoader loader = new RecordLoader();
            Record record = loader.load(getDirectory().resolve(key));
            cachedRecords.put(new Key(key), record);
            lastRecordFromCache = false;
            return record;
        } catch (IOException ignored){

        }
        throw new RuntimeException("Problem loading record: " + key);
    }

    public boolean wasLastRecordInCache(){
        return lastRecordFromCache;
    }
}
