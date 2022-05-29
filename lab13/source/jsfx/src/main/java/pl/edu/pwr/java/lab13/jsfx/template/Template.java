package pl.edu.pwr.java.lab13.jsfx.template;

import lombok.*;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Template {
    @Element
    private String templateName;

    @ElementList(type = Field.class)
    private List<Field> fields;

    @Element
    private String templateString;

    @Override
    public String toString(){
        return templateName;
    }
}
