package pl.edu.pwr.java.lab13.jsfx.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Field {
    @Element
    private String label;
    @Element
    private String fieldId;
}
