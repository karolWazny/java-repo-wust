package persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

@Root
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MapEntity {
    @Element
    private String engineName;

    @Element
    private int width;

    @Element
    private int height;

    @ElementArray
    private int[][] cells;
}
