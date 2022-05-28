package persistence;

import engine.Map;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.nio.file.Path;

public class Persistence {
    public void saveAs(Map map, Path path) throws Exception {
        Serializer serializer = new Persister();
        MapEntity entity = MapEntity.builder()
                .cells(map.getCells())
                .height(map.getHeight())
                .width(map.getWidth())
                .engineName(map.getEngineName())
                .build();
        File result = path.toFile();

        serializer.write(entity, result);
    }

    public Map read(Path path){
        throw new UnsupportedOperationException();
    }
}
